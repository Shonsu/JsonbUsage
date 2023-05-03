package pl.shonsu.jsonbusage.module;

public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_MODERATOR("MODERATOR");

    private String name;

    Role(String role) {
        this.name = role;
    }

    public String getName() {
        return name;
    }
}
