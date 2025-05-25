package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.application.api.patient.dtos.PatientRegistrationRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.profile.Profile;
import br.com.sorriso.domain.user.User;
import br.com.sorriso.infrastructure.exceptions.FrontDisplayableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdatePatientUseCase {
    private final PatientService patientService;
    private final ClinicService clinicService;

    public Optional<?> handler(
            User user,
            UUID id,
            PatientRegistrationRequest request
    ) {
        var clinic = clinicService.getByUser(user).orElseThrow(()->
                new FrontDisplayableException(
                        HttpStatus.BAD_REQUEST,
                        "Clinic not found"
                )
        );

        var patient = patientService.getByIdAndClinic(id, clinic).orElseThrow(()->
            new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Patient not found"
            )
        );

        Profile profile = patient.getProfile();

        profile.setPhoto(request.getPhoto());
        profile.setDocument(request.getDocument());
        profile.setBirthDate(request.getBirthDate());
        profile.setName(request.getName());

        return Optional.of(patientService.save(patient));
    }
}
