package br.com.starter.domain.user;

import br.com.starter.domain.auth.Auth;
import br.com.starter.domain.privilege.Privilege;
import br.com.starter.domain.profile.Profile;
import br.com.starter.domain.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    private UUID id = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_id", referencedColumnName = "id")
    private Auth auth;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "privileges_on_users",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
    private List<Privilege> privileges;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
