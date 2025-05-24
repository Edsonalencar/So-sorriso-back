package br.com.sorriso.application.api.patient;

import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.patient.dto.PatientRegistrationRequest;
import br.com.sorriso.application.useCase.patient.*;
import br.com.sorriso.domain.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sorriso/api/patientes")
@RequiredArgsConstructor
public class PatientController {
    private final CreatePatientUseCase createPatientUseCase;
    private final GetPatientUseCase readPatientUseCase;
    private final GetAllPatientsUseCase readAllPatientsUseCase;
    private final DeletePatientUseCase deletePatientUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;

    @GetMapping
    public ResponseEntity<?> getAll(
            @AuthenticationPrincipal CustomUserDetails userAuthentication
            ) {
        var user = userAuthentication.getUser();

        return ResponseEntity.ok(new ResponseDTO<>(readAllPatientsUseCase.handler(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        var user = userAuthentication.getUser();

        return ResponseEntity.ok(new ResponseDTO<>(readPatientUseCase.handler(user, id)));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails userAuthentication,
                                    @PathVariable UUID id){
        deletePatientUseCase.handler(id, userAuthentication.getUser());

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @RequestBody PatientRegistrationRequest request
    ) {
        var user = userAuthentication.getUser();

        return ResponseEntity.ok(new ResponseDTO<>(createPatientUseCase.handler(user, request)));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@AuthenticationPrincipal CustomUserDetails userAuthentication,
                                    @PathVariable UUID id,
                                    @RequestBody PatientRegistrationRequest request){
        var user = userAuthentication.getUser();

        return ResponseEntity.ok(new ResponseDTO<>(updatePatientUseCase.handler(user, id, request)));
    }
}