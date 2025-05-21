package br.com.sorriso.application.useCase.stockTransaction;

import br.com.sorriso.application.api.stockTransaction.dtos.OutputStockItemDTO;
import br.com.sorriso.application.api.stockTransaction.dtos.OutputStockTransactionRequest;
import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.stockItem.StockItemService;
import br.com.sorriso.domain.stockTransaction.StockTransaction;
import br.com.sorriso.domain.stockTransaction.StockTransactionService;
import br.com.sorriso.domain.stockTransaction.TransactionType;
import br.com.sorriso.domain.transactionItem.TransactionItem;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OutputStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final StockItemService stockItemService;
    private final ClinicService clinicService;

    public Optional<StockTransaction> handler(
        User user,
        OutputStockTransactionRequest request
    ) {
        Clinic clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        if (request.getType() != TransactionType.OUTPUT) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Somente saída e orçamento!"
            );
        }

        var stockTransaction = new StockTransaction();
        stockTransaction.setType(request.getType());
        stockTransaction.setClinic(clinic);
        stockTransaction.setCategory(request.getCategory());
        stockTransaction.setOwner(user);
        stockTransaction.setTransactionDate(request.getTransactionAt());

        // TODO -> Implementar atendimento
        // if (request.getWorkOrder() != null)
        //    stockTransaction.setWorkOrder(request.getWorkOrder());

        var transActionTotalQuantity = request.getItems().stream()
                .map(OutputStockItemDTO::getQuantity)
                .reduce(0, Integer::sum);

        var transActionTotalItemsPrice = request.getItems().stream()
                .map(OutputStockItemDTO::getPrice)
                .reduce(0L, Long::sum);

        stockTransaction.setQuantity(transActionTotalQuantity);
        stockTransaction.setPrice(transActionTotalItemsPrice * transActionTotalQuantity);

        List<TransactionItem> transactionItems = new ArrayList<>();

        request.getItems().forEach(item -> {
            var transactionitem = getTransactionitem(stockTransaction, item);
            transactionItems.add(transactionitem);
        });

        if(stockTransaction.getItems() == null)
            stockTransaction.setItems(new ArrayList<>());

        stockTransaction.setItems(transactionItems);

        return Optional.of(stockTransactionService.save(stockTransaction));
    }

    public TransactionItem getTransactionitem (
        StockTransaction transaction,
        OutputStockItemDTO itemRequest
    ) {
        var transActionItem = new TransactionItem();
        transActionItem.setQuantity(itemRequest.getQuantity());
        transActionItem.setTransaction(transaction);
        transActionItem.setPrice(itemRequest.getPrice());
        transActionItem.setDiscount(itemRequest.getDiscount());

        var stockItem = stockItemService.findById(
            itemRequest.getStockItemId(),
            transaction.getClinic()
        ).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Item não encontrado!"
            )
        );

        if(transaction.getType() == TransactionType.OUTPUT) {
            if(stockItem.getQuantity() < itemRequest.getQuantity()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O estoque não possui esta quantidade de items!"
                );
            }

            stockItem.setQuantity(stockItem.getQuantity() - itemRequest.getQuantity());
        }
        
        stockItem = stockItemService.save(stockItem);

        transActionItem.setStockItem(stockItem);

        return transActionItem;
    }
}
