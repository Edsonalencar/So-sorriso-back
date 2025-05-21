package br.com.sorriso.application.useCase.stockTransaction;

import br.com.sorriso.application.api.stockTransaction.dtos.GetPageStockTransactionRequest;
import br.com.sorriso.domain.clinic.ClinicService;

import br.com.sorriso.domain.stockTransaction.StockTransactionService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final ClinicService clinicService;

    public Optional<?> handler(
        User user,
        GetPageStockTransactionRequest request,
        Integer page
    ) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var ids = stockTransactionService.getPageFilterIds(clinic, request);

        var pageRequest = PageRequest.of(page, request.getSize());

        var content = ids != null
            ? stockTransactionService.getAllByIds(
                ids,
                request.getTransactionType(),
                pageRequest
            )
            : stockTransactionService.findAllByClinicAndType(
                clinic.getId(),
                request.getTransactionType(),
                pageRequest
            );

        return Optional.of(content);
    }
}
