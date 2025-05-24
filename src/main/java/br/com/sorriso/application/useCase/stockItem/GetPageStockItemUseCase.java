package br.com.sorriso.application.useCase.stockItem;

import br.com.sorriso.application.api.stockItem.dto.GetPageStockItemRequest;
import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.stockItem.StockItemService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageStockItemUseCase {
    private final StockItemService stockItemService;
    private final ClinicService clinicService;

    public Optional<?> handler(
        User user,
        GetPageStockItemRequest request,
        Integer page
    ) {
        Clinic clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var ids = stockItemService.getPageFilterIds(clinic, request);

        var pageRequest = PageRequest.of(page, request.getSize());

        var content = ids != null
            ? stockItemService.getAllByIds(
                clinic.getId(),
                ids,
                pageRequest
            )
            : stockItemService.findAllByClinicId(
                clinic.getId(),
                pageRequest
            );

        return Optional.of(content);
    }
}
