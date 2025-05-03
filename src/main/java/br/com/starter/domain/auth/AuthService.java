package br.com.starter.domain.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Auth> createAuth(String username, String rawPassword) {
        if (authRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito, usuário já existe!");
        }

        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setPassword(passwordEncoder.encode(rawPassword)); // Codifica corretamente a senha

        Auth savedAuth = authRepository.save(auth);
        if (savedAuth != null) {
            return new ResponseEntity<>(savedAuth, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro, criação de autenticação falhou!");
        }
    }

    // Atualizar a senha de um usuário
    public ResponseEntity<String> updatePassword(UUID authId, String rawPassword) {
        return authRepository.findById(authId).map(auth -> {
            auth.setPassword(passwordEncoder.encode(rawPassword)); // Codifica corretamente a senha
            Auth updatedAuth = authRepository.save(auth);
            if (updatedAuth != null){
                return ResponseEntity.ok("Senha atualizada.");}
            else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na atualização da senha!");}
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciais não encontradas!"));
    }

    // Atualizar o nome de usuário
    public ResponseEntity<String> updateUsername(UUID authId, String newUsername) {
        return authRepository.findById(authId).map(auth -> {
            if (authRepository.existsByUsername(newUsername)
                    && !authRepository.findByUsername(newUsername).get().getId().equals(authId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito, usuário já existe!");
            }
            auth.setUsername(newUsername);
            Auth updatedAuth = authRepository.save(auth);
            if (updatedAuth != null){
                return ResponseEntity.ok("Nome de usuário atualizado.");}
            else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na atualização do nome de usuário!");}
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciais não encontradas!"));
    }

    // Buscar credenciais por nome de usuário
    public ResponseEntity<Auth> getByUsername(String username) {
        return authRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));
    }

    public ResponseEntity<Auth> getById(UUID id) {
        return authRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciais não encontradas!"));
    }

    public ResponseEntity<Void> deleteById(UUID id) {
        if (!authRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credenciais não encontradas!");
        }
        authRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public boolean usernameExists(String username) {
        return authRepository.existsByUsername(username);
    }	
}

