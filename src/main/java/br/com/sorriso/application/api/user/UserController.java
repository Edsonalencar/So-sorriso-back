package br.com.sorriso.application.api.user;

import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.user.dto.AuthRequestDTO;
import br.com.sorriso.application.api.user.dto.UpdateUserDTO;
import br.com.sorriso.application.api.user.dto.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sorriso.domain.user.CustomUserDetails;
import br.com.sorriso.domain.user.UserService;

@RestController
@RequestMapping("/sorriso/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{userId}")
    public ResponseEntity<?> update (
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable("userId") UUID userId,
            @RequestBody UpdateUserDTO request
    ) throws Exception {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.update(
                    userId,
                    request
                )
            )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.login(
                    authRequest.username(),
                    authRequest.password()
                )
            )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest user) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.create(user)
            )
        );
    }

    @PatchMapping("/{userId}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable UUID userId, @RequestParam String newPassword) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.updatePassword(userId, newPassword)
            )
        );
    }

    @PatchMapping("/{userId}/update-username")
    public ResponseEntity<?> updateUsername(@PathVariable UUID userId, @RequestParam String newUsername) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.updateUsername(userId, newUsername)
            )
        );
    }

    @PatchMapping("/{userId}/privileges/{privilegeId}/add")
    public ResponseEntity<?> addPrivilegeToUser(@PathVariable UUID userId, @PathVariable UUID privilegeId) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.addPrivilegeToUser(userId, privilegeId)
            )
        );
    }

    @PatchMapping("/{userId}/privileges/{privilegeId}/remove")
    public ResponseEntity<?> removePrivilegeFromUser(@PathVariable UUID userId,  @PathVariable UUID privilegeId) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.removePrivilegeFromUser(userId, privilegeId)
            )
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.getUserById(userId)
            )
        );
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                userService.deleteById(userId)
            )
        );
    }
}
