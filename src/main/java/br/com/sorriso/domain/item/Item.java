package br.com.sorriso.domain.item;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.user.ActiveStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String description;
    private String code;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status = ActiveStatus.ACTIVE;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}