package br.com.sorriso.domain.item;

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
public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("""
        SELECT i FROM Item i
        WHERE i.clinic.id= :clinicId
        AND i.id = :id
    """)
    Optional<Item> findByIdAndClinic(
        @Param("id") UUID id,
        @Param("clinicId") UUID clinicId
    );

    @Query("""
        SELECT i FROM Item i
        WHERE i.clinic.id= :clinicId
        AND i.status = :status
    """)
    List<Item> findAllByClinicAndQuery(
        @Param("clinicId") UUID clinicId,
        @Param("status") ActiveStatus status
    );

    @Query("""
        SELECT i FROM Item i
        WHERE i.clinic.id= :clinicId
        AND i.status = :status
        AND (
            :query IS NULL
            OR LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(i.code) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Page<Item> findPageAllByClinicAndQuery(
        @Param("clinicId") UUID clinicId,
        @Param("status") ActiveStatus status,
        @Param("query") String query,
        Pageable pageable
    );
}
