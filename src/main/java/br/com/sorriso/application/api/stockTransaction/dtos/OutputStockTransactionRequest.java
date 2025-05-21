package br.com.sorriso.application.api.stockTransaction.dtos;

import br.com.sorriso.domain.stockTransaction.TransactionCategory;
import br.com.sorriso.domain.stockTransaction.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OutputStockTransactionRequest {
    private List<OutputStockItemDTO> items;
    private LocalDateTime transactionAt;
    private TransactionCategory category;
    private TransactionType type = TransactionType.OUTPUT;
    // TODO -> Implementar atendimento
}
