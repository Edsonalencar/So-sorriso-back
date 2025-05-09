package br.com.sorriso.domain.role;

import lombok.Getter;

import java.util.UUID;


@Getter
public enum RoleType {
    ROLE_DENTIST(UUID.fromString("2f9c3d5a-7e8b-4f1d-9c6b-8d7e5f4c3b2a"), "DENTIST"),
    ROLE_ATTENDANT(UUID.fromString("3e8d4c5b-9f2a-5e1c-8d7b-6c5d4e3f2c1b"), "ATTENDANT"),
    ROLE_ADMIN(UUID.fromString("4f7e6d5c-8a3b-6f2e-7d8c-5f4e3d2c1b0a"), "ADMIN");

    private final UUID id;
    private final String name;

    RoleType(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
