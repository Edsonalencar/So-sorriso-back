package br.com.sorriso.application.api.user.dto;

import br.com.sorriso.domain.role.RoleType;
import br.com.sorriso.domain.user.ActiveStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String name;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private ActiveStatus status = null;
    private RoleType role = RoleType.ROLE_ADMIN;
}