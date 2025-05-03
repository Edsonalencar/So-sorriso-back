package br.com.sorriso.domain.clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;

    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public Optional<Clinic> findById(UUID id) {
        return clinicRepository.findById(id);
    }

}
