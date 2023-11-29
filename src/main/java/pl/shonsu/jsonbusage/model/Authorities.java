package pl.shonsu.jsonbusage.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public class Authorities {
    @Enumerated(EnumType.STRING)
    List<Role> roles;

    public Authorities(List<Role> roles) {
        this.roles = roles;
    }

    public Authorities() {
    }

    public List<Role> getRoles() {
        return roles;
    }
}
