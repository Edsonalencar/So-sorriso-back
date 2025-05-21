package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.application.api.patient.dto.PatientRegistrationRequest;
import br.com.sorriso.domain.patient.Patient;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdatePatientUseCase {
    private final PatientService patientService;

    public Optional<?> handler(UUID id, PatientRegistrationRequest request) {
        Optional<Patient> patient = patientService.findById(id);

        if(patient.isEmpty()) {
            return Optional.empty();
        }

        Profile profile = patient.get().getProfile();

        if(request.getPhoto() != null) {
            patient.get().getProfile().setPhoto(request.getPhoto());
        }

        if(request.getDocument() != null) {
            patient.get().getProfile().setDocument(request.getDocument());
        }

        if (request.getBirthDate() != null) {
            patient.get().getProfile().setBirthDate(request.getBirthDate());
        }

        if(request.getName() != null) {
            patient.get().getProfile().setName(request.getName());
        }

        return Optional.of(patientService.save(patient.get()));
    }
}
