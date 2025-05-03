package br.com.starter.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, UUID> {

    Optional<Auth> findByUsername(String username);

    boolean existsByUsername(String username);
}
