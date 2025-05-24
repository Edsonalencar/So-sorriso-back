package br.com.sorriso.domain.clinic;

import br.com.sorriso.domain.user.ActiveStatus;
import br.com.sorriso.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, UUID> {
    Optional<Clinic> findByOwner(User user);

    @Query("""
        SELECT c FROM Clinic c
        WHERE c.owner.status = :status
        AND (
           :query IS NULL
           OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(c.owner.profile.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Page<Clinic> findPageByStatusAndNames(
        @Param("query") String query,
        @Param("status") ActiveStatus status,
        Pageable pageable
    );
}
