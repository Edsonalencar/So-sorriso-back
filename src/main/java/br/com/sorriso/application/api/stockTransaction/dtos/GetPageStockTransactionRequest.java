package br.com.sorriso.application.api.stockTransaction.dtos;

import br.com.sorriso.domain.stockTransaction.TransactionCategory;
import br.com.sorriso.domain.stockTransaction.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GetPageStockTransactionRequest {
    private Integer size = 10;
    private Set<UUID> itemsIs;
    private Set<UUID> ids;
    private UUID ownerId;
    @NotNull
    private TransactionType transactionType;
    private TransactionCategory category;
    private String query;
}