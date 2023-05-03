package pl.shonsu.jsonbusage.module;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import pl.shonsu.jsonbusage.AddressJsonType;

import java.util.List;

@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;
    private String username;

    @JdbcTypeCode(SqlTypes.JSON)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Type(AddressJsonType.class)
    //@JdbcTypeCode(SqlTypes.JSON)
    private Address address;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Address getAddress() {
        return address;
    }
}
