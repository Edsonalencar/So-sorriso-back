package br.com.sorriso.application.useCase.local;

import br.com.sorriso.application.api.local.dtos.UpdateLocalStatusRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.local.LocalService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateStatusLocalUseCase {
    private final LocalService localService;
    private final ClinicService clinicService;

    public Optional<?> handler(
        User user,
        UUID localId,
        UpdateLocalStatusRequest request
    ) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        var local = localService.getByIdAndClinicId(localId, clinic.getId()).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Item não encontrado!"
            )
        );

        local.setStatus(request.getStatus());

        return Optional.of(localService.save(local));
    }
}
