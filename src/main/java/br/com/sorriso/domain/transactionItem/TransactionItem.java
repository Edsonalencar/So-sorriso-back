package br.com.sorriso.domain.transactionItem;

import br.com.sorriso.domain.stockItem.StockItem;
import br.com.sorriso.domain.stockTransaction.StockTransaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_item")
@Getter
@Setter
public class TransactionItem {
    @Id
    private UUID id = UUID.randomUUID();

    private Integer quantity;
    private Long price;
    private Long discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_item_id", nullable = false)
    private StockItem stockItem;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private StockTransaction transaction;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
