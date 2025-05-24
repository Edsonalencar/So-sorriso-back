package br.com.sorriso.application.useCase.item;

import br.com.sorriso.application.api.item.dtos.CreateItemRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.item.Item;
import br.com.sorriso.domain.item.ItemService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateItemUseCase {
    private final ItemService itemService;
    private final ClinicService clinicService;

    public Optional<?> handler(User user, CreateItemRequest request) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        var item = new Item();
        if (request.getCode() != null)
            item.setCode(request.getCode());
        item.setClinic(clinic);
        item.setName(request.getName());
        item.setDescription(request.getDescription());

        return Optional.of(itemService.save(item));
    }
}
