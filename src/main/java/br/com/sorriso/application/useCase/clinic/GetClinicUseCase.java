package br.com.sorriso.application.useCase.clinic;

import br.com.sorriso.domain.clinic.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetClinicUseCase {
    private final ClinicService clinicService;

    public Optional<?> handler(UUID clinicId) {
        return clinicService.getById(clinicId);
    }
}
