package br.com.sorriso.domain.stockItem;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.item.Item;
import br.com.sorriso.domain.local.Local;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_item")
@Getter
@Setter
public class StockItem {
    @Id
    private UUID id = UUID.randomUUID();

    private Long acquisitionPrice;
    private Long price;
    private Integer quantity = 0;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = true)
    private Local local;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate acquisitionAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
