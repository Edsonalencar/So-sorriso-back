package br.com.sorriso.application.useCase.item;

import br.com.sorriso.application.api.common.GetPageRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.item.ItemService;
import br.com.sorriso.domain.user.ActiveStatus;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageItemUseCase {
    private final ItemService itemService;
    private final ClinicService clinicService;

    public Optional<?> handler(
        User user,
        Integer page,
        GetPageRequest request
    ) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        return Optional.of(itemService.findPageAllByClinicAndQuery(
            clinic,
            request.getQuery(),
            ActiveStatus.valueOf(request.getStatus().name()),
            PageRequest.of(page, request.getSize())
        ));
    }
}
