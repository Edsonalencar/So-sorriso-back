package br.com.sorriso.application.useCase.stockTransaction;

import br.com.sorriso.application.api.stockTransaction.dtos.OutputStockItemDTO;
import br.com.sorriso.application.api.stockTransaction.dtos.OutputStockTransactionRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.stockItem.StockItem;
import br.com.sorriso.domain.stockItem.StockItemService;
import br.com.sorriso.domain.stockTransaction.StockTransaction;
import br.com.sorriso.domain.stockTransaction.StockTransactionService;
import br.com.sorriso.domain.stockTransaction.TransactionType;
import br.com.sorriso.domain.transactionItem.TransactionItem;
import br.com.sorriso.domain.transactionItem.TransactionItemService;
import br.com.sorriso.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateOutStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final TransactionItemService transactionItemService;
    private final StockItemService stockItemService;
    private final ClinicService clinicService;

    @Transactional
    public Optional<?> handler(
        User user,
        UUID stockTransactionId,
        OutputStockTransactionRequest request
    ) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
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

        var stockTransaction = stockTransactionService.getByIdAndClinicId(
            stockTransactionId,
            clinic.getId()
        ).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Essa movimentação de estoque não pode ser encontrada!"
            )
        );

        stockTransaction.setCategory(request.getCategory());
        stockTransaction.setTransactionDate(request.getTransactionAt());

        // TODO -> Implementar atendimento
        // if (request.getWorkOrder() != null)
        //      stockTransaction.setWorkOrder(request.getWorkOrder());

        var transActionTotalQuantity = request.getItems().stream()
                .map(OutputStockItemDTO::getQuantity)
                .reduce(0, Integer::sum);

        var transActionTotalItemsPrice = request.getItems().stream()
                .map(OutputStockItemDTO::getPrice)
                .reduce(0L, Long::sum);

        stockTransaction.setQuantity(transActionTotalQuantity);
        stockTransaction.setPrice(transActionTotalItemsPrice * transActionTotalQuantity);
        stockTransaction.setTransactionDate(request.getTransactionAt());

        List<TransactionItem> transactionItems = new ArrayList<>();

        request.getItems().forEach(item -> {
            var transactionitem = updateTransactionitem(stockTransaction, item);
            transactionItems.add(transactionitem);
        });

        transactionItemService.deleteAll(stockTransaction.getItems());
        stockTransaction.getItems().clear();
        stockTransaction.setItems(transactionItems);

        stockTransaction.setType(request.getType());

        return Optional.of(stockTransactionService.save(stockTransaction));
    }

    private TransactionItem updateTransactionitem(
        StockTransaction stockTransaction,
        OutputStockItemDTO itemRequest
    ) {
        var transActionItem = new TransactionItem();
        transActionItem.setTransaction(stockTransaction);
        transActionItem.setQuantity(itemRequest.getQuantity());
        transActionItem.setPrice(itemRequest.getPrice());
        transActionItem.setDiscount(itemRequest.getDiscount());

        var stockItem = stockItemService.findById(
            itemRequest.getStockItemId(),
            stockTransaction.getClinic()
        ).orElseThrow();

        if(stockTransaction.getType() != TransactionType.QUOT) {
            StockItem finalStockItem = stockItem;
            var transActionitem =stockTransaction.getItems().stream()
                    .filter(item -> item.getStockItem().getId() == finalStockItem.getId())
                    .findFirst().orElseThrow();

            stockItem.setQuantity(stockItem.getQuantity() + transActionitem.getQuantity());
        }

        if(stockTransaction.getType() == TransactionType.OUTPUT) {
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
