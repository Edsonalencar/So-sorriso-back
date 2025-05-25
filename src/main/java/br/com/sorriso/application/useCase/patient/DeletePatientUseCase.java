package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.user.User;
import br.com.sorriso.infrastructure.exceptions.FrontDisplayableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeletePatientUseCase {
    private final PatientService patientService;
    private final ClinicService clinicService;

    public void handler(
        UUID id,
        User user
    ){
        var clinic = clinicService.getByUser(user).orElseThrow(()->
                new FrontDisplayableException(
                        HttpStatus.BAD_REQUEST,
                        "Clinic not found"
                )
        );

        var patient = patientService.getByIdAndClinic(id, clinic).orElseThrow(()->
                new FrontDisplayableException(
                        HttpStatus.BAD_REQUEST,
                        "Patient not found"
                )
        );

        patientService.delete(patient);
    }
}
