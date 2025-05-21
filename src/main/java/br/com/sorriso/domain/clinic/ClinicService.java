package br.com.sorriso.domain.clinic;

import br.com.sorriso.domain.user.ActiveStatus;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;

    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public List<Clinic> getAll() {
        return clinicRepository.findAll();
    }

    public Optional<Clinic> getById(UUID id) {
        return clinicRepository.findById(id);
    }

    public Page<Clinic> findAllPage(Pageable pageable) {
        return clinicRepository.findAll(pageable);
    }

    public Page<Clinic> getPageByOwnerStatusAndName(
        String query,
        ActiveStatus userStatus,
        Pageable pageable
    ) {
        return clinicRepository.findPageByStatusAndNames(
            query,
            userStatus,
            pageable
        );
    }

    public Optional<Clinic> getByUser(User user) {return clinicRepository.findByOwner(user);}
}
