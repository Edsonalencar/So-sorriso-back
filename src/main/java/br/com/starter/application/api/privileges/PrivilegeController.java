package br.com.starter.application.api.privileges;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.domain.privilege.PrivilegeService;

import java.util.UUID;

@RestController
@RequestMapping("/starter/api/privileges")
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @PostMapping
    public ResponseEntity<?> createPrivilege(@RequestParam String name) {
        ResponseDTO<?> response = new ResponseDTO<>(privilegeService.createPrivilege(name));
        return ResponseEntity.ok(response);    
    }

    @GetMapping
    public ResponseEntity<?> getAllPrivileges(Pageable pageable) {
        ResponseDTO<?> response = new ResponseDTO<>(privilegeService.getAllPrivileges(pageable));
        return ResponseEntity.ok(response);    
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrivilegeById(@PathVariable UUID id) {
        ResponseDTO<?> response = new ResponseDTO<>(privilegeService.getPrivilegeById(id));
        return ResponseEntity.ok(response);    
    }

    @GetMapping("/search")
    public ResponseEntity<?> getPrivilegeByName(@RequestParam String name) {
        ResponseDTO<?> response = new ResponseDTO<>(privilegeService.getPrivilegeByName(name));
        return ResponseEntity.ok(response);    
    }

    @PatchMapping("/{id}/signature-revoked")
    public ResponseEntity<?> updateIsSignatureRevoked(@PathVariable UUID id, @RequestParam boolean isSignatureRevoked) {
        ResponseDTO<?> response = new ResponseDTO<>(privilegeService.updateIsSignatureRevoked(id, isSignatureRevoked));
        return ResponseEntity.ok(response);    
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrivilege(@PathVariable UUID id, @RequestParam String newName) {
        ResponseDTO<?> response = new ResponseDTO<>(privilegeService.updatePrivilege(id, newName));
        return ResponseEntity.ok(response);    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrivilege(@PathVariable UUID id) {
        ResponseDTO<?> response = new ResponseDTO<>(privilegeService.deletePrivilege(id));
        return ResponseEntity.ok(response);
    }
}

