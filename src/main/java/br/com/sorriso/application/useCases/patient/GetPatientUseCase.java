package br.com.sorriso.application.useCases.patient;

import br.com.sorriso.domain.patient.Patient;
import br.com.sorriso.domain.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPatientUseCase {
    private final PatientService patientService;

    public Optional<Optional<Patient>> handler(UUID id){
        return Optional.of(patientService.findById(id));
    }
}
