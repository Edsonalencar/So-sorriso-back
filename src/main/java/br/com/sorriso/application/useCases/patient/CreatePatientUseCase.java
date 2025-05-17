package br.com.sorriso.application.useCases.patient;

import br.com.sorriso.application.api.user.dto.UserRegistrationRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.patient.Patient;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.profile.Profile;
import br.com.sorriso.domain.role.RoleType;
import br.com.sorriso.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreatePatientUseCase {
    private final PatientService patientService;


    public Optional<Patient> handler(UserRegistrationRequest request) {
        var profile = new Profile();
        profile.setName(request.get);
        profile.setPhone(request.getPhone());
        profile.setAddress(request.ge);


        var patient = new Patient();
        patient.setProfile();

    }
}
