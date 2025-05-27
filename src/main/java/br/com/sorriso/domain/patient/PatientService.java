package br.com.sorriso.domain.patient;

import br.com.sorriso.application.api.patient.dtos.GetPagePatientRequest;
import br.com.sorriso.domain.clinic.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Optional<List<Patient>> getAllAndClinicId(UUID clinicId) { return patientRepository.findAllByClinicId(clinicId); } ;

    public Page<Patient> getAllByClinicId(UUID clinicId, Pageable pageable) { return this.patientRepository.findAllByClinicId(clinicId, pageable); }

    public Page<Patient> getAllByIds(UUID clinicId, Set<UUID> ids, Pageable pageable){ return this.patientRepository.findAllById(clinicId, ids, pageable); };

    public Set<UUID> getPageFilterIds(
        Clinic clinic,
        GetPagePatientRequest request
    ) {
        Set<UUID> mapper = null;

        if(request.getPatientIds() != null && !request.getPatientIds().isEmpty()) {
            var ids = patientRepository.findByPatientsFilter(clinic.getId(), request.getPatientIds());
            mapper = mountMapper(mapper, ids);
        }

        if(request.getName() != null && !request.getName().isEmpty()) {
            var ids = patientRepository.findByNameFilter(clinic.getId(), request.getName());
            mapper = mountMapper(mapper, ids);
        }

        if(request.getDocument() != null && !request.getDocument().isEmpty()) {
            var ids = patientRepository.findByDocumentFilter(clinic.getId(), request.getDocument());
            mapper = mountMapper(mapper, ids);
        }

        return mapper;
    }

    private static Set<UUID> mountMapper(Set<UUID> mapper, Set<UUID> ids) {
        if (mapper == null)
            mapper = new HashSet<>();

        if (mapper.isEmpty()) mapper.addAll(ids);
        else mapper.retainAll(ids);

        return mapper;
    }
}