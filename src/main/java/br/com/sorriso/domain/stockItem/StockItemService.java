package br.com.sorriso.domain.stockItem;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.application.api.stockItem.dto.GetPageStockItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StockItemService {
    private final StockItemRepository stockItemRepository;

    public StockItem save(StockItem stockItem) {
        return stockItemRepository.save(stockItem);
    }

    public List<StockItem> getAllByClinic(UUID clinicId) {
        return stockItemRepository.findAllWithItems(clinicId);
    }

    public Optional<StockItem> findByItemAndPrice(UUID itemId, UUID clinicId, UUID localId , Long price) {
        return stockItemRepository.findByItemAndAcquisitionPriceAndStatus(itemId, clinicId, localId, price);
    }

    public Optional<StockItem> findById(UUID id, Clinic clinic) {
        return stockItemRepository.findByIdAndClinicId(clinic.getId(), id);
    }

    public Page<StockItem> findAllByClinicId(UUID clinicId, Pageable pageable) {
        return stockItemRepository.findAllByClinicId(clinicId, pageable);
    }

    public Page<StockItem> getAllByIds(UUID clinicId, Set<UUID> ids, Pageable pageable) {
        return stockItemRepository.findAllByIds(clinicId, ids, pageable);
    }

    public Set<UUID> getPageFilterIds (
        Clinic clinic,
        GetPageStockItemRequest request
    ) {
        Set<UUID> mapper = null;

        if (request.getIds() != null && !request.getIds().isEmpty()) {
            var ids = request.getIds();
            mapper = new HashSet<>(ids);
        }

        if (request.getItemsIs() != null && !request.getItemsIs().isEmpty()) {
            var ids = stockItemRepository.findByItemsFilter(
                clinic.getId(),
                request.getItemsIs()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            var ids = stockItemRepository.findByItemNameFilters(
                clinic.getId(),
                request.getQuery()
            );

            mapper = mountMapper(mapper, ids);
        }

        return mapper;
    }

    private static Set<UUID> mountMapper(Set<UUID> mapper, Set<UUID> ids) {
        if (mapper == null)
            mapper = new HashSet<>();

        if (mapper.isEmpty()) mapper.addAll(ids);
        else mapper.retainAll(ids);

        return mapper;
    }
}
