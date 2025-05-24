package br.com.sorriso.application.useCase.stockItem;

import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.stockItem.StockItemService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetAllStockItemUseCase {
    private final StockItemService stockItemService;
    private final ClinicService clinicService;

    public Optional<?> handler(
        User user
    ) {
        var garage = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        return Optional.of(stockItemService.getAllByClinic(
            garage.getId()
        ));
    }
}
