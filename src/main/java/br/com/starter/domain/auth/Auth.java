package br.com.starter.domain.auth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "auths")
@Getter
@Setter
public class Auth {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(unique = true)
    private String username = null;
    @JsonIgnore
    private String password = null;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
