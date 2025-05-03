package br.com.starter.domain.privilege;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "privileges")
@Setter
@Getter
public class Privilege {
    @Id
    private UUID id = UUID.randomUUID();
    private String name = null;
    private Boolean isSignatureRevoked = false;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
