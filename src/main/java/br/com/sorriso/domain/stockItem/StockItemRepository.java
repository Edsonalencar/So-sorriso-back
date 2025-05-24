package br.com.sorriso.domain.stockItem;

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
public interface StockItemRepository extends JpaRepository<StockItem, UUID> {

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.clinic.id = :clinicId
        AND s.quantity > 0
    """)
    List<StockItem> findAllWithItems(
        @Param("clinicId") UUID clinicId
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.clinic.id = :clinicId
        AND s.item.id = :itemId
        AND s.acquisitionPrice = :acquisitionPrice
        AND s.local.id = :localId
    """)
    Optional<StockItem> findByItemAndAcquisitionPriceAndStatus(
        @Param("itemId") UUID itemId,
        @Param("clinicId") UUID clinicId,
        @Param("localId") UUID localId,
        @Param("acquisitionPrice") Long acquisitionPrice
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.clinic.id = :clinicId
    """)
    Page<StockItem> findAllByClinicId(
        @Param("clinicId") UUID clinicId,
        Pageable pageable
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.clinic.id = :clinicId
        AND s.id in :ids
    """)
    Page<StockItem> findAllByIds(
        @Param("clinicId") UUID clinicId,
        @Param("ids") Set<UUID> ids,
        Pageable pageable
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.clinic.id = :clinicId
        AND s.id = :id
    """)
    Optional<StockItem>  findByIdAndClinicId(
        @Param("clinicId") UUID clinicId,
        @Param("id") UUID id
    );

    @Query("""
        SELECT COUNT(DISTINCT s.item) FROM StockItem s
        WHERE s.clinic.id = :clinicId
    """)
    int countDistinctItemsByClinicId(@Param("clinicId") UUID clinicId);

    @Query("""
        SELECT COALESCE(SUM(s.quantity), 0) FROM StockItem s
        WHERE s.clinic.id = :clinicId
    """)
    int sumQuantityByClinicId(@Param("clinicId") UUID clinicId);

    @Query("""
        SELECT distinct s.id FROM StockItem s
        WHERE s.clinic.id = :clinicId
        AND s.item.id in :itemIds
    """)
    Set<UUID> findByItemsFilter(
        @Param("clinicId") UUID clinicId,
        @Param("itemIds") Set<UUID> itemIds
    );

    @Query("""
        SELECT distinct s.id  FROM StockItem s
        WHERE s.clinic.id = :clinicId
        AND (
            :query IS NULL
            OR LOWER(s.item.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Set<UUID> findByItemNameFilters(
        @Param("clinicId") UUID clinicId,
        @Param("query") String query
    );
}
