package br.com.sorriso.application.useCase.clinic;

import br.com.sorriso.application.api.user.dto.UpdateUserDTO;
import br.com.sorriso.application.api.user.dto.UserRegistrationRequest;
import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateClinicUseCase {
    private final ClinicService clinicService;
    private final UserService userService;

    @Transactional
    public Optional<Clinic> handler(UUID clinicId, UserRegistrationRequest request) {

        var clinic = clinicService.getById(clinicId).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Clinica não encontrada!"
            )
        );

        clinic.setName(request.getName());

        ModelMapper mapper = new ModelMapper();

        var updateRequest = mapper.map(request, UpdateUserDTO.class);
        updateRequest.setName(request.getName());

        var user = userService.update(clinic.getOwner().getId(), updateRequest);
        clinic.setOwner(user);

        return Optional.of(clinicService.save(clinic));
    }
}
