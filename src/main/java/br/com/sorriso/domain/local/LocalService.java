package br.com.sorriso.domain.local;

import br.com.sorriso.domain.clinic.Clinic;
import br.com.sorriso.domain.user.ActiveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalService {
    private final LocalRepository localRepository;

    public Local save(Local local) {
        return localRepository.save(local);
    }

    public Optional<Local> getByIdAndClinicId(UUID localId, UUID clinicId) {
        return localRepository.findByIdAndClinicId(clinicId, localId);
    }

    public void delete(Local local) {
        localRepository.delete(local);
    }

    public List<Local> findAllByClinicAndQuery(Clinic clinic) {
        return localRepository.findAllByClinicAndQuery(
            clinic.getId(),
            ActiveStatus.ACTIVE
        );
    }

    public Page<Local> findAllByClinicAndQuery(Clinic clinic, String query, ActiveStatus status, Pageable pageable) {
        return localRepository.findAllByClinicAndQuery(
            clinic.getId(),
            status,
            query,
            pageable
        );
    }
}
