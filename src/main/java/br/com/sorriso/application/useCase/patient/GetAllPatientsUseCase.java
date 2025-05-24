package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetAllPatientsUseCase {
    private final PatientService patientService;
    private final ClinicService clinicService;

    public Optional<?> handler(User user){
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Clinic not found"
                )
        );

        return Optional.of(patientService.findAll());
    }
}
