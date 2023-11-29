package pl.shonsu.jsonbusage.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

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
    private Authorities authorities;

    @Type(AddressJsonType.class)
    //@JdbcTypeCode(SqlTypes.JSON)
    private Address address;

    @Enumerated(EnumType.STRING)
    private List<UserType> userTypes;

    public User(Long id, String username, Authorities authorities, Address address, List<UserType> userTypes) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.address = address;
        this.userTypes = userTypes;
    }

    public User() {
        // for hibernate
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Authorities getAuthorities() {
        return authorities;
    }

    public Address getAddress() {
        return address;
    }

    public List<UserType> getUserTypes() {
        return userTypes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", authorities=" + authorities +
                ", address=" + address +
                ", userTypes=" + userTypes +
                '}';
    }
}
