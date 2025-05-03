package br.com.starter.application.api.user.dto;

import br.com.starter.domain.user.UserStatus;
import lombok.Data;

@Data
public class UpdateUserStatusDTO {
    private UserStatus status;
}
