package br.com.sorriso.application.api.stockTransaction.dtos;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.stockTransaction.StockTransaction;
import br.com.sorriso.domain.stockTransaction.TransactionCategory;
import br.com.sorriso.domain.stockTransaction.TransactionStatus;
import br.com.sorriso.domain.stockTransaction.TransactionType;
import br.com.sorriso.domain.transactionItem.TransactionItem;
import br.com.sorriso.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StockTransactionDTO {
    private UUID id;
    private Long price;
    private Integer quantity;
    private TransactionCategory category;
    private TransactionStatus status;
    private TransactionType type;
    private Clinic clinic;
    private User owner;
    // TODO -> Implementar atendimento

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private List<TransactionItem> items;

    public StockTransactionDTO(StockTransaction transaction) {
        this.id = transaction.getId();
        this.price = transaction.getPrice();
        this.quantity = transaction.getQuantity();
        this.category = transaction.getCategory();
        this.status = transaction.getStatus();
        this.type = transaction.getType();
        this.clinic = transaction.getClinic();
        this.owner = transaction.getOwner();
        this.transactionDate = transaction.getTransactionDate();
        this.createdAt = transaction.getCreatedAt();
        this.items = transaction.getItems();
    }
}
