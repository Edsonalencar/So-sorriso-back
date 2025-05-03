package br.com.starter.domain.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.starter.domain.privilege.Privilege;
import br.com.starter.domain.privilege.PrivilegeService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeService privilegeService;

    public RoleService(RoleRepository roleRepository, PrivilegeService privilegeService) {
        this.roleRepository = roleRepository;
        this.privilegeService = privilegeService;
    }

    // Criar uma Role com ou sem privilégios
    @Transactional
    public Role createRole(String name, List<UUID> privilegeIds) {
        if (roleRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role já existe com este nome!");
        }

        Role role = new Role();
        role.setName(name);

        if (privilegeIds != null && !privilegeIds.isEmpty()) {
            // Valida e busca os privilégios
            List<Privilege> privileges = privilegeIds.stream()
                    .map(privilegeService::getPrivilegeById)
                    .collect(Collectors.toList());
            role.setPrivileges(privileges);
        }

        Role savedRole = roleRepository.save(role);
        return savedRole;
    }

    public Page<Role> getAllRoles(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles;
    }

    public Role getRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada!"));
    }

    // Adicionar privilégios a uma Role
    @Transactional
    public Role addPrivilegesToRole(UUID roleId, List<UUID> privilegeIds) {
        // Verifica se a role existe
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada!"));
    
        // Busca privilégios válidos do banco
        List<Privilege> validPrivileges = privilegeService.findAllByIds(privilegeIds);
    
        // Verifica se todos os privilégios fornecidos existem
        if (validPrivileges.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum dos privilégios fornecidos foi encontrado!");
        }
    
        if (validPrivileges.size() != privilegeIds.size()) {
            List<UUID> missingPrivileges = privilegeIds.stream()
                    .filter(id -> validPrivileges.stream().noneMatch(privilege -> privilege.getId().equals(id)))
                    .toList();
    
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Os seguintes privilégios não existem: " + missingPrivileges
            );
        }
    
        // Adiciona os privilégios válidos
        validPrivileges.forEach(privilege -> {
            if (!role.getPrivileges().contains(privilege)) {
                role.getPrivileges().add(privilege);
            }
        });
    
        // Salva a role com os novos privilégios
        Role updatedRole = roleRepository.save(role);
    
        return updatedRole;
    }
    

    // Remover privilégios de uma Role
    @Transactional
    public Role removePrivilegesFromRole(UUID roleId, List<UUID> privilegeIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada!"));
    
        // Remover privilégios
        privilegeIds.forEach(privilegeId -> {
            if (roleRepository.isPrivilegeAssignedToRole(roleId, privilegeId)) {
                roleRepository.removePrivilegeFromRole(roleId, privilegeId);
            }
        });
    
        // Atualiza a entidade com os privilégios removidos
        List<Privilege> updatedPrivileges = role.getPrivileges().stream()
                .filter(privilege -> !privilegeIds.contains(privilege.getId()))
                .toList();
    
        role.setPrivileges(updatedPrivileges);
    
        return role;
    }

    @Transactional
    public Role updateRoleName(UUID roleId, String newName) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada!"));

        if (roleRepository.existsByName(newName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role já existe com este nome!");
        }

        role.setName(newName);
        Role updatedRole = roleRepository.save(role);

        return updatedRole;
    }

    @Transactional
    public boolean deleteRole(UUID roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada!");
        }
        roleRepository.deleteById(roleId);
        return true;
    }
}
