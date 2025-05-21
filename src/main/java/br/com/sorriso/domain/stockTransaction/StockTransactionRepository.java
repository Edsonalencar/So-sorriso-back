package br.com.sorriso.domain.stockTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, UUID> {

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.id in :ids
    """)
    List<StockTransaction> findAllByIds(
        @Param("ids") Set<UUID> ids
    );

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.id in :ids
        AND s.type = :type
    """)
    Page<StockTransaction> findAllByIds(
        @Param("ids") Set<UUID> ids,
        @Param("type") TransactionType type,
        Pageable pageable
    );

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.clinic.id = :clinicId
        AND s.id in :id
    """)
    Optional<StockTransaction> findByIdAndClinicId(
        @Param("clinicId") UUID clinicId,
        @Param("id") UUID id
    );

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        JOIN s.items i
        WHERE s.clinic.id = :clinicId
        AND i.stockItem.item.id in :itemIds
    """)
   Set<UUID> findByItemsFilter(
        @Param("clinicId") UUID clinicId,
       @Param("itemIds") Set<UUID> itemIds
   );

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        WHERE s.clinic.id = :clinicId
        AND s.owner.id = :ownerId
    """)
    Set<UUID> findByOwnerFilter(
        @Param("clinicId") UUID clinicId,
        @Param("ownerId") UUID ownerId
    );

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.clinic.id = :clinicId
        AND s.type = :type
    """)
    Page<StockTransaction> findAllByClinicAndType(
        @Param("clinicId") UUID clinicId,
        @Param("type") TransactionType type,
        Pageable pageable
    );

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        WHERE s.clinic.id = :clinicId
        AND s.category = :category
    """)
    Set<UUID> findByTransactionCategoryFilter(
        @Param("clinicId") UUID clinicId,
        @Param("category") TransactionCategory category
    );

    @Query("""
        SELECT distinct s.id  FROM StockTransaction s
        JOIN s.items i
        WHERE s.clinic.id = :clinicId
        AND (
            :query IS NULL
            OR LOWER(i.stockItem.item.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Set<UUID> findByItemNameFilters(
        @Param("clinicId") UUID clinicId,
        @Param("query") String query
    );
}
