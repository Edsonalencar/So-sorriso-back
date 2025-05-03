package br.com.sorriso.application.api.user.dto;

import br.com.sorriso.domain.user.UserStatus;
import lombok.Data;

@Data
public class UpdateUserStatusDTO {
    private UserStatus status;
}
