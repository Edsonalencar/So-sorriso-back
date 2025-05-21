package br.com.sorriso.application.useCase.local;

import br.com.sorriso.application.api.local.dtos.CreateLocalStockRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.local.Local;
import br.com.sorriso.domain.local.LocalService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateLocalUserCase {
    private final ClinicService clinicService;
    private final LocalService localService;

    public Optional<?> handler(User user, CreateLocalStockRequest request) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        ModelMapper mapper = new ModelMapper();

        var local = mapper.map(request, Local.class);
        local.setClinic(clinic);

        return Optional.of(localService.save(local));
    }
}
