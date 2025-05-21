package br.com.sorriso.domain.local;

import br.com.sorriso.domain.user.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocalRepository extends JpaRepository<Local, UUID> {

    @Query("""
        SELECT l FROM Local l
        WHERE l.clinic.id= :clinicId
        AND l.status = :status
    """)
    List<Local>  findAllByClinicAndQuery(
        @Param("clinicId") UUID clinicId,
        @Param("status") ActiveStatus status
    );

    @Query("""
        SELECT l FROM Local l
        WHERE l.clinic.id= :clinicId
        AND l.status = :status
        AND (
            :query IS NULL
            OR LOWER(l.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Page<Local> findAllByClinicAndQuery(
        @Param("clinicId") UUID clinicId,
        @Param("status") ActiveStatus status,
        @Param("query") String query,
        Pageable pageable
    );

    @Query("""
        SELECT l FROM Local l
        WHERE l.clinic.id= :clinicId
        AND l.id = :id
    """)
    Optional<Local> findByIdAndClinicId(
        @Param("clinicId") UUID clinicId,
        @Param("id") UUID id
    );
}
