package br.com.sorriso.application.api.common;

import br.com.sorriso.domain.user.ActiveStatus;
import lombok.Data;

@Data
public class GetAllRequest {
    private String query = "";
    private ActiveStatus status = ActiveStatus.ACTIVE;
}
