package br.com.sorriso.application.api.local.dtos;

import br.com.sorriso.domain.user.ActiveStatus;
import lombok.Data;

@Data
public class UpdateLocalStatusRequest {
    private ActiveStatus status;
}
