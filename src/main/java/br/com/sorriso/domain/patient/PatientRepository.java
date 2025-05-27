package br.com.sorriso.domain.patient;

import br.com.sorriso.domain.clinic.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByIdAndClinic(UUID id, Clinic clinic);

    Optional<List<Patient>> findAllByClinicId(UUID clinicId);

    Page<Patient> findAllByClinicId(UUID clinicId, Pageable pageable);

    Page<Patient> findAllById(UUID clinicId, Set<UUID> ids, Pageable pageable);

    @Query("""
        SELECT p.id FROM Patient p
        WHERE p.clinic.id = :clinicId 
        AND p.id IN :patientIds
    """)
    Set<UUID> findByPatientsFilter(@Param("clinicId") UUID clinicId, @Param("patientIds") Set<UUID> patientIds);

    @Query("""
        SELECT p.id FROM Patient p
        WHERE p.clinic.id = :clinicId 
        AND p.profile.name = :name 
    """)
    Set<UUID> findByNameFilter(
        @Param("clinicId") UUID clinicId,
        @Param("name") String name
    );

    @Query("""
        SELECT p.id FROM Patient p
        WHERE p.clinic.id = :clinicId 
        AND p.profile.document = :document
    """)
    Set<UUID> findByDocumentFilter(
        @Param("clinicId") UUID clinicId,
        @Param("document") String document
    );
}