package br.com.sorriso.application.useCase.stockTransaction;

import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.stockItem.StockItemService;
import br.com.sorriso.domain.stockTransaction.StockTransaction;
import br.com.sorriso.domain.stockTransaction.StockTransactionService;
import br.com.sorriso.domain.stockTransaction.TransactionStatus;
import br.com.sorriso.domain.stockTransaction.TransactionType;
import br.com.sorriso.domain.transactionItem.TransactionItem;
import br.com.sorriso.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CancelStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final StockItemService stockItemService;
    private final ClinicService clinicService;

    @Transactional
    public Optional<?> handler(
        User user,
        UUID stockTransactionId
    ) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var stockTransaction = stockTransactionService.getByIdAndClinicId(
            stockTransactionId,
            clinic.getId()
        ).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Essa movimentação de estoque não pode ser encontrada!"
            )
        );

        stockTransaction.getItems().forEach(item -> cancelTransactionItem(stockTransaction, item));
        stockTransaction.setStatus(TransactionStatus.CANCELLED);

        return Optional.of(stockTransactionService.save(stockTransaction));
    }

    private void cancelTransactionItem (StockTransaction transaction, TransactionItem item) {
        var stockItem = stockItemService.findById(
            item.getStockItem().getId(),
            transaction.getClinic()
        ).orElseThrow();

       if(transaction.getType() == TransactionType.INPUT) {
           stockItem.setQuantity( stockItem.getQuantity() - item.getQuantity());
       } else if(transaction.getType() == TransactionType.OUTPUT){
           stockItem.setQuantity( stockItem.getQuantity() + item.getQuantity());
       }

       stockItemService.save(stockItem);
    }
}
