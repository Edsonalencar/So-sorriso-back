package br.com.sorriso.application.api.common;

import br.com.sorriso.domain.user.ActiveStatus;
import lombok.Data;

@Data
public class GetPageRequest {
    private Integer size = 10;
    private String query = "";
    private ActiveStatus status = ActiveStatus.ACTIVE;
}
