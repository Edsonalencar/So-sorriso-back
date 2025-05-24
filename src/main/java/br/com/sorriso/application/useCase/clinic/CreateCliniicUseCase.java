package br.com.sorriso.application.useCase.clinic;

import br.com.sorriso.application.api.user.dto.UserRegistrationRequest;
import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.role.RoleType;
import br.com.sorriso.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateCliniicUseCase {

    private final ClinicService clinicService;
    private final UserService userService;

    public Optional<Clinic> handler(UserRegistrationRequest request) {

        request.setRole(RoleType.ROLE_DENTIST);
        var user = userService.create(request);

        var clinic = new Clinic();
        clinic.setOwner(user);

        return  Optional.of( clinicService.save(clinic));
    }
}
