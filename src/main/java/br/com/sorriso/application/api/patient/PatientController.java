package br.com.sorriso.application.api.patient;

import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.user.dto.UserRegistrationRequest;
import br.com.sorriso.application.useCases.patient.CreatePatientUseCase;
import br.com.sorriso.application.useCases.patient.GetAllPatientsUseCase;
import br.com.sorriso.application.useCases.patient.GetPatientUseCase;
import br.com.sorriso.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    @GetMapping
    public ResponseEntity<?> getAll(
            @AuthenticationPrincipal CustomUserDetails userAuthentication
            ) {

        return ResponseEntity.ok(new ResponseDTO<>(readAllPatientsUseCase.handler()) );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {

        return ResponseEntity.ok(new ResponseDTO<>(readPatientUseCase.handler(id)) );
    }

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @RequestBody UserRegistrationRequest request
    )
}