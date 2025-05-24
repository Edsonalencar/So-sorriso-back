package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.application.api.patient.dto.PatientRegistrationRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.patient.Patient;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.profile.Profile;
import br.com.sorriso.domain.user.User;
import br.com.sorriso.infrastructure.exceptions.FrontDisplayableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreatePatientUseCase {
    private final PatientService patientService;
    private final ClinicService clinicService;

    public Optional<?> handler(User user, PatientRegistrationRequest request) {
        Patient newPatient = new Patient();

        var clinic = clinicService.getByUser(user).orElseThrow(()->
                new FrontDisplayableException(
                        HttpStatus.BAD_REQUEST,
                        "Clinic not found"
                )
        );

        var profile = new Profile();

        profile.setPhoto(request.getPhoto());
        profile.setName(request.getName());
        profile.setPhone(request.getPhone());
        profile.setBirthDate(request.getBirthDate());
        profile.setDocument(request.getDocument());
        newPatient.setProfile(profile);

        return Optional.of(patientService.save(newPatient));
    }
}
