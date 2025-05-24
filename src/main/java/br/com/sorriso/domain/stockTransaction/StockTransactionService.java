package br.com.sorriso.domain.stockTransaction;

import br.com.sorriso.application.api.stockTransaction.dtos.GetPageStockTransactionRequest;
import br.com.sorriso.domain.clinic.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StockTransactionService {
    private final StockTransactionRepository stockTransactionRepository;

    public StockTransaction save(StockTransaction stockTransaction) {
        return stockTransactionRepository.save(stockTransaction);
    }

    public Optional<StockTransaction> getByIdAndClinicId(UUID id, UUID clinicId) {
        return stockTransactionRepository.findByIdAndClinicId(clinicId, id);
    }

    public List<StockTransaction> getAll() {
        return stockTransactionRepository.findAll();
    }

    public Page<StockTransaction> getAll(Pageable pageable) {
        return stockTransactionRepository.findAll(pageable);
    }

    public Page<StockTransaction> findAllByClinicAndType(UUID clinicId, TransactionType type, Pageable pageable) {
        return stockTransactionRepository.findAllByClinicAndType(clinicId, type,pageable);
    }

    public List<StockTransaction> getAllByIds(Set<UUID> ids) {
        return stockTransactionRepository.findAllByIds(ids);
    }

    public Page<StockTransaction> getAllByIds(Set<UUID> ids, TransactionType type, Pageable pageable) {
        return stockTransactionRepository.findAllByIds(ids, type, pageable);
    }

    public Set<UUID> getPageFilterIds (
        Clinic clinic,
        GetPageStockTransactionRequest request
    ) {
        Set<UUID> mapper = null;

        if (request.getIds() != null && !request.getIds().isEmpty()) {
            var ids = request.getIds();
            mapper = new HashSet<>(ids);
        }

        if (request.getItemsIs() != null && !request.getItemsIs().isEmpty()) {
            var ids = stockTransactionRepository.findByItemsFilter(
                clinic.getId(),
                request.getItemsIs()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getOwnerId() != null) {
            var ids = stockTransactionRepository.findByOwnerFilter(
                clinic.getId(),
                request.getOwnerId()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getCategory() != null) {
            var ids = stockTransactionRepository.findByTransactionCategoryFilter(
                clinic.getId(),
                request.getCategory()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            var ids = stockTransactionRepository.findByItemNameFilters(
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
