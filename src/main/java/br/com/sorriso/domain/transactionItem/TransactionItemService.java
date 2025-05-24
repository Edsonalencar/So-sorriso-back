package br.com.sorriso.domain.transactionItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionItemService {

    private final TransactionItemRepository transactionItemRepository;

    public TransactionItem save(TransactionItem transactionItem) {
        return transactionItemRepository.save(transactionItem);
    }

    public void deleteAll(List<TransactionItem> transactionItems) {
        transactionItemRepository.deleteAll(transactionItems);
    }

    public void delete(TransactionItem transactionItem) {
        transactionItemRepository.delete(transactionItem);
    }
}
