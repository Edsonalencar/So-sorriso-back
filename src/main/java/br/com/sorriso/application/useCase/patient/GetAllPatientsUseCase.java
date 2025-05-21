package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.domain.patient.Patient;
import br.com.sorriso.domain.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetAllPatientsUseCase {
    private final PatientService patientService;

    public Optional<List<Patient>> handler(){
        return Optional.of(patientService.findAll());
    }
}
