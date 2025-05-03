package br.com.sorriso.application.api.user.dto;

import br.com.sorriso.application.api.common.AddressDTO;
import br.com.sorriso.domain.user.UserStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDTO {
    private String name;
    private String email;
    private String document;
    private String phone;
    private String password;
    private UserStatus status = null;
    private LocalDate birthDate = null;
    private AddressDTO address = null;
}
