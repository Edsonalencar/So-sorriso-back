package br.com.starter.application.api.user;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.user.dto.AuthRequestDTO;
import br.com.starter.application.api.user.dto.UpdateUserDTO;
import br.com.starter.application.api.user.dto.UserRegistrationRequest;
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

import br.com.starter.domain.user.CustomUserDetails;
import br.com.starter.domain.user.UserService;

@RestController
@RequestMapping("/starter/api/users")
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
        ResponseDTO<?> response = new ResponseDTO<>(userService.login(authRequest.username(), authRequest.password()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest user) {
        ResponseDTO<?> response = new ResponseDTO<>(userService.create(user));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable UUID userId, @RequestParam String newPassword) {
        ResponseDTO<?> response = new ResponseDTO<>(userService.updatePassword(userId, newPassword));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/update-username")
    public ResponseEntity<?> updateUsername(@PathVariable UUID userId, @RequestParam String newUsername) {
        ResponseDTO<?> response = new ResponseDTO<>(userService.updateUsername(userId, newUsername));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/privileges/{privilegeId}/add")
    public ResponseEntity<?> addPrivilegeToUser(@PathVariable UUID userId, @PathVariable UUID privilegeId) {
        ResponseDTO<?> response = new ResponseDTO<>(userService.addPrivilegeToUser(userId, privilegeId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/privileges/{privilegeId}/remove")
    public ResponseEntity<?> removePrivilegeFromUser(@PathVariable UUID userId,  @PathVariable UUID privilegeId) {
        ResponseDTO<?> response = new ResponseDTO<>(userService.removePrivilegeFromUser(userId, privilegeId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
        ResponseDTO<?> response = new ResponseDTO<>(userService.getUserById(userId));
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        ResponseDTO<?> response = new ResponseDTO<>(userService.deleteById(userId));
        return ResponseEntity.ok(response);
    }
}
