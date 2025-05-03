package br.com.starter.domain.privilege;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, UUID>{
    Page<Privilege> findAll(Pageable pageable);


    Optional<Privilege> findByName(String name);

    boolean existsByName(String name);
}
