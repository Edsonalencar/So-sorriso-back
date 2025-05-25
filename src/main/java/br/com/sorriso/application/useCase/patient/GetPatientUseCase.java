package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.patient.PatientService;
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
public class GetPatientUseCase {
    private final PatientService patientService;
    private final ClinicService clinicService;

    public Optional<?> handler(
        User user,
        UUID id
    ){
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

        return Optional.of(patient);
    }
}
