package br.com.starter.domain.role;

import lombok.Getter;

@Getter
public enum RoleType {
    ROLE_USER("USER"),
    ROLE_MANAGER("MANAGER"),
    ROLE_ADMIN("ADMIN");

    private final String name;

    RoleType(String simpleName) {
        this.name = simpleName;
    }
}
