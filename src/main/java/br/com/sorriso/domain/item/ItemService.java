package br.com.sorriso.domain.item;

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
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Page<Item> getAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> getById(UUID id, Clinic clinic) {
        return itemRepository.findByIdAndClinic(id, clinic.getId());
    }

    public List<Item> findAllByClinicAndQuery(Clinic clinic) {
        return itemRepository.findAllByClinicAndQuery(
                clinic.getId(),
                ActiveStatus.ACTIVE
        );
    }

    public Page<Item> findPageAllByClinicAndQuery(
        Clinic clinic,
        String query,
        ActiveStatus status,
        Pageable pageable
    ) {
        return itemRepository.findPageAllByClinicAndQuery(
            clinic.getId(),
            status,
            query,
            pageable
        );
    }
}
