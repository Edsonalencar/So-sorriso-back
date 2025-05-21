package br.com.sorriso.domain.clinic;

import br.com.sorriso.domain.patient.Patient;
import br.com.sorriso.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clinics")
@Getter
@Setter
public class Clinic {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @OneToOne
    private User owner;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<Patient> patients;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
