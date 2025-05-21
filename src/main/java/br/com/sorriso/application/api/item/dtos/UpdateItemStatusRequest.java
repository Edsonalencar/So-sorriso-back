package br.com.sorriso.application.api.item.dtos;

import br.com.sorriso.domain.user.ActiveStatus;
import lombok.Data;

@Data
public class UpdateItemStatusRequest {
    private ActiveStatus status;
}
