package br.com.sorriso.domain.patient;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.profile.Profile;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient {
    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne
    private Profile profile;

    @ManyToOne
    private Clinic clinic;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
