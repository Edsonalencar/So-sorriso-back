package br.com.sorriso.application.api.stockItem.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GetPageStockItemRequest {
    private Integer size = 10;
    private Set<UUID> itemsIs;
    private Set<UUID> ids;
    private String query;
}
