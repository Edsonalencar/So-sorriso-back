package br.com.sorriso.application.useCase.clinic;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAllClinicsUseCase {
    private final ClinicService clinicService;
    private final UserService userService;

    @Transactional
    public List<Clinic> handler(UUID userId) {
        // Verifica se o usuário existe
        userService.getUserById(userId);

        return clinicService.getAll();
    }
}
