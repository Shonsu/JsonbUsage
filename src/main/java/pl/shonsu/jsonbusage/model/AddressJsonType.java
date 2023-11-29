package pl.shonsu.jsonbusage.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.shonsu.jsonbusage.model.Address;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class AddressJsonType implements UserType<Address> {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public int getSqlType() {
        return SqlTypes.JSON;
    }

    @Override
    public Class<Address> returnedClass() {
        return Address.class;
    }

    @Override
    public boolean equals(Address address, Address address1) {
        LOGGER.info("equals()");
        if (address == address1) {
            return true;
        } else if (address == null || address1 == null) {
            return false;
        } else {
            return Objects.equals(address.city(), address1.city()) &&
                    Objects.equals(address.street(), address1.street()) &&
                    Objects.equals(address.number(), address1.number());
        }
    }

    @Override
    public int hashCode(Address address) {
        return Objects.hash(address.city(), address.street(), address.number());
    }

    @Override
    public Address nullSafeGet(ResultSet resultSet, int position,
                               SharedSessionContractImplementor sharedSessionContractImplementor,
                               Object o) throws SQLException {
        LOGGER.info("nullSafeGet()");
        final String cellContent = resultSet.getString(position);
        if (cellContent == null) {
            return null;
        }
        try {
            return MAPPER.readValue(cellContent.getBytes(StandardCharsets.UTF_8), returnedClass());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert String to AddressJsonType: " + e.getMessage(), e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement,
                            Address address,
                            int index,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        LOGGER.info("nullSafeSet()");
        if (address == null) {
            preparedStatement.setNull(index, Types.OTHER);
            return;
        }
        final StringWriter writer = new StringWriter();
        try {
            MAPPER.writeValue(writer, address);
            writer.flush();
            preparedStatement.setObject(index, writer.toString(), Types.OTHER);
        } catch (IOException e) {
            throw new RuntimeException("Filed to convert AddressJsonType to String" + e.getMessage(), e);
        }

    }

    @Override
    public Address deepCopy(Address address) {
        LOGGER.info("deepCopy()");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(address);
            oos.flush();
            oos.close();
            bos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
            Address result = (Address) new ObjectInputStream(bais).readObject();
            bais.close();
            return result;
        } catch (ClassNotFoundException | IOException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Address address) {
        LOGGER.info("disassemble()");
        return address;
    }

    @Override
    public Address assemble(Serializable serializable, Object o) {
        LOGGER.info("assemble()");
        return (Address) serializable;
    }

    @Override
    public Address replace(Address address, Address j1, Object o) {
        LOGGER.info("replace()");
        return deepCopy(address);
    }
}
