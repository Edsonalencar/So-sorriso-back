package br.com.sorriso.domain.patient;

import br.com.sorriso.domain.clinic.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public Patient save(Patient patient) { return patientRepository.save(patient); }

    public Optional<Patient> findById(UUID id) { return patientRepository.findById(id); }

    public List<Patient> findAll() { return patientRepository.findAll(); }

    public void deleteById(UUID id) { this.patientRepository.deleteById(id); }

    public void delete(Patient patient) { patientRepository.delete(patient); }

    public Optional<Patient> getByIdAndClinic(UUID id, Clinic clinic) { return patientRepository.findByIdAndClinic(id, clinic); }

}