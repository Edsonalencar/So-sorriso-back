package br.com.sorriso.application.api.stockTransaction.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class InputStockItemDTO {
    private UUID stockItemId = null; // para atualizar determinado item de estoque
    private UUID itemId;
    private UUID localId;
    private Long acquisitionUnitPrice;

    private Long price;
    private Integer quantity;
}
