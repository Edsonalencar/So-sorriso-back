package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.application.api.patient.dto.PatientRegistrationRequest;
import br.com.sorriso.domain.patient.Patient;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreatePatientUseCase {
    private final PatientService patientService;

    public Optional<Patient> handler(PatientRegistrationRequest request) {
        Patient newPatient = new Patient();

        Profile profile = new Profile();

        profile.setPhoto(request.getPhoto());
        profile.setName(request.getName());
        profile.setPhone(request.getPhone());
        profile.setBirthDate(request.getBirthDate());
        profile.setDocument(request.getDocument());

        newPatient.setProfile(profile);

        return Optional.of(patientService.save(newPatient));
    }
}
