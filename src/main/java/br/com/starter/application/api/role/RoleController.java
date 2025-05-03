package br.com.starter.application.api.role;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.domain.role.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/starter/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestParam String name, @RequestBody(required = false) List<UUID> privilegeIds) {
        ResponseDTO<?> response = new ResponseDTO<>(roleService.createRole(name, privilegeIds));
        return ResponseEntity.ok(response); 
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles(Pageable pageable) {
        ResponseDTO<?> response = new ResponseDTO<>(roleService.getAllRoles(pageable));
        return ResponseEntity.ok(response); 
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable UUID id) {
        ResponseDTO<?> response = new ResponseDTO<>(roleService.getRoleById(id));
        return ResponseEntity.ok(response); 
    }

    @PostMapping("/{id}/privileges")
    public ResponseEntity<?> addPrivilegesToRole(@PathVariable UUID id, @RequestBody List<UUID> privilegeIds) {
        ResponseDTO<?> response = new ResponseDTO<>(roleService.addPrivilegesToRole(id, privilegeIds));
        return ResponseEntity.ok(response); 
    }

    @DeleteMapping("/{id}/privileges")
    public ResponseEntity<?> removePrivilegesFromRole(@PathVariable UUID id, @RequestBody List<UUID> privilegeIds) {
        ResponseDTO<?> response = new ResponseDTO<>(roleService.removePrivilegesFromRole(id, privilegeIds));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<?> updateRoleName(@PathVariable UUID id, @RequestParam String newName) {
        ResponseDTO<?> response = new ResponseDTO<>(roleService.updateRoleName(id, newName));
        return ResponseEntity.ok(response); 
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable UUID id) {
        ResponseDTO<?> response = new ResponseDTO<>(roleService.deleteRole(id));
        return ResponseEntity.ok(response); 
    }
}
