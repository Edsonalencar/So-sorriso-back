package br.com.starter.domain.privilege;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Transactional
    public Privilege createPrivilege(String name) {
        if (privilegeRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Privilégio já existente com o mesmo nome!");
        }

        Privilege privilege = new Privilege();
        privilege.setName(name);

        Privilege savedPrivilege = privilegeRepository.save(privilege);
        return savedPrivilege;
    }

    // Buscar todos os privilégios com paginação
    public Page<Privilege> getAllPrivileges(Pageable pageable) {
        Page<Privilege> privileges = privilegeRepository.findAll(pageable);
        return privileges;
    }

    public Privilege getPrivilegeById(UUID id) {
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilégio não encontrado!"));
    }

    // Buscar privilégio por nome
    public Privilege getPrivilegeByName(String name) {
        return privilegeRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilégio não encontrado!"));
    }

    @Transactional
    public List<Privilege> findAllByIds(List<UUID> ids) {
        // Busca os privilégios existentes pelos IDs fornecidos
        List<Privilege> privileges = privilegeRepository.findAllById(ids);

        // Verifica se existem privilégios faltantes
        List<UUID> missingIds = ids.stream()
                .filter(id -> privileges.stream().noneMatch(privilege -> privilege.getId().equals(id)))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Os seguintes privilégios não foram encontrados: " + missingIds);
        }

        return privileges;
    }

    // Atualizar o estado de isSignatureRevoked
    @Transactional
    public Privilege updateIsSignatureRevoked(UUID id, boolean isSignatureRevoked) {
        return privilegeRepository.findById(id).map(privilege -> {
            privilege.setIsSignatureRevoked(isSignatureRevoked);
            return privilegeRepository.save(privilege);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilégio não encontrado!"));
    }

    // Atualizar o nome do privilégio
    @Transactional
    public Privilege updatePrivilege(UUID id, String newName) {
        return privilegeRepository.findById(id).map(privilege -> {
            if (!privilege.getName().equals(newName) && privilegeRepository.existsByName(newName)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um privilégio com esse nome!");
            }
            privilege.setName(newName);
            Privilege updatedPrivilege = privilegeRepository.save(privilege);
            return updatedPrivilege;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilégio não encontrado!"));
    }

    @Transactional
    public boolean deletePrivilege(UUID id) {
        if (!privilegeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilégio não encontrado!");
        }
        privilegeRepository.deleteById(id);
        return true;
    }
}
