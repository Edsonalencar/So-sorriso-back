package br.com.sorriso.application.useCase.stockTransaction;

import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.application.api.stockTransaction.dtos.StockTransactionDTO;
import br.com.sorriso.domain.stockTransaction.StockTransactionService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final ClinicService clinicService;

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
        );

        if (!stockTransaction.isPresent())
            return Optional.empty();

        return Optional.of(
            new StockTransactionDTO(
                stockTransaction.orElseThrow()
            )
        );
    }
}
