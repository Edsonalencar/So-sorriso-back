package br.com.sorriso.application.useCase.stockTransaction;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.application.api.stockTransaction.dtos.InputStockItemDTO;
import br.com.sorriso.application.api.stockTransaction.dtos.InputStockTransactionRequest;
import br.com.sorriso.domain.item.ItemService;
import br.com.sorriso.domain.local.LocalService;
import br.com.sorriso.domain.stockItem.StockItem;
import br.com.sorriso.domain.stockItem.StockItemService;
import br.com.sorriso.domain.stockTransaction.StockTransaction;
import br.com.sorriso.domain.stockTransaction.StockTransactionService;
import br.com.sorriso.domain.stockTransaction.TransactionType;
import br.com.sorriso.domain.transactionItem.TransactionItem;
import br.com.sorriso.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InputStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final StockItemService stockItemService;
    private final ClinicService clinicService;
    private final LocalService localService;
    private final ItemService itemService;

    @Transactional
    public Optional<?> handler(
        User user,
        InputStockTransactionRequest request
    ) {
        if(request.getItems().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possivel realizar uma transação de estoque vazia!"
            );
        }

        var clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        if(request.getItems().isEmpty()) {
            throw  new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possivel realizar uma transação de estoque vazia!"
            );
        }

       var stockTransaction = getStockTransaction(user, clinic, request);

       if(stockTransaction.getItems() == null)
           stockTransaction.setItems(new ArrayList<>());

        request.getItems().forEach(item -> {
            var stockItem = getStockItem(clinic, item, stockTransaction);
            stockTransaction.getItems().add(stockItem);
        });

        var savedTransaction = stockTransactionService.save(stockTransaction);

        return Optional.of(savedTransaction);
    }

    public StockTransaction getStockTransaction(
        User user,
        Clinic clinic,
        InputStockTransactionRequest request
    ) {
        var stockTransaction = new StockTransaction();
        stockTransaction.setType(TransactionType.INPUT);
        stockTransaction.setClinic(clinic);
        stockTransaction.setOwner(user);

        stockTransaction.setCategory(request.getCategory());
        stockTransaction.setTransactionDate(request.getTransactionAt());

        var transActionTotalQuantity = request.getItems().stream()
            .map(InputStockItemDTO::getQuantity)
            .reduce(0, Integer::sum);

        var transActionTotalItemsPrice = request.getItems().stream()
            .map(InputStockItemDTO::getAcquisitionUnitPrice)
            .reduce(0L, Long::sum);

        stockTransaction.setQuantity(transActionTotalQuantity);
        stockTransaction.setPrice(transActionTotalItemsPrice * transActionTotalQuantity);

        return stockTransaction;
    }

    public TransactionItem getStockItem (
        Clinic clinic,
        InputStockItemDTO itemRequest,
        StockTransaction stockTransaction
    ) {
        var transActionItem = new TransactionItem();
        transActionItem.setTransaction(stockTransaction);
        transActionItem.setQuantity(itemRequest.getQuantity());
        transActionItem.setPrice(itemRequest.getPrice());

        var stockItem = stockItemService.findByItemAndPrice(
            itemRequest.getItemId(),
            clinic.getId(),
            itemRequest.getLocalId(),
            itemRequest.getAcquisitionUnitPrice()
        ).orElse(null);

        if (stockItem == null) {
            var item = itemService.getById(itemRequest.getItemId(), clinic).orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Item não encontrado!"
                )
            );

            if (itemRequest.getLocalId() != null) {
                var local = localService.getByIdAndClinicId(itemRequest.getLocalId(), clinic.getId()).orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Local não encontrado!"
                    )
                );

                stockItem.setLocal(local);
            }

            stockItem = new StockItem();

            stockItem.setClinic(clinic);
            stockItem.setItem(item);
            stockItem.setAcquisitionPrice(itemRequest.getAcquisitionUnitPrice());
            stockItem.setPrice(itemRequest.getPrice());
            stockItem.setAcquisitionAt(stockTransaction.getTransactionDate().toLocalDate());
        }

        stockItem.setQuantity(stockItem.getQuantity() + itemRequest.getQuantity());

        stockItem = stockItemService.save(stockItem);

        transActionItem.setStockItem(stockItem);

        return transActionItem;
    }
}
