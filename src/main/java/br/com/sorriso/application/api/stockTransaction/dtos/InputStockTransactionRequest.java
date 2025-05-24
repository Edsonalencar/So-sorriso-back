package br.com.sorriso.application.api.stockTransaction.dtos;

import br.com.sorriso.domain.stockTransaction.TransactionCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InputStockTransactionRequest {
    private List<InputStockItemDTO> items;
    private TransactionCategory category;
    private LocalDateTime transactionAt;
}
