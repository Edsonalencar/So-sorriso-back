package br.com.sorriso.application.api.patient;

import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.patient.dtos.GetPagePatientRequest;
import br.com.sorriso.application.api.patient.dtos.PatientRegistrationRequest;
import br.com.sorriso.application.useCase.patient.*;
import br.com.sorriso.domain.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sorriso/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final CreatePatientUseCase createPatientUseCase;
    private final GetPatientUseCase getPatientUseCase;
    private final GetAllPatientsUseCase getAllPatientsUseCase;
    private final DeletePatientUseCase deletePatientUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;
    private final GetPagePatientUseCase getPagePatientUseCase;

    @GetMapping
    public ResponseEntity<?> getAll(
            @AuthenticationPrincipal CustomUserDetails userAuthentication
            ) {
        var user = userAuthentication.getUser();

        return ResponseEntity.ok(new ResponseDTO<>(getAllPatientsUseCase.handler(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        var user = userAuthentication.getUser();

        return ResponseEntity.ok(new ResponseDTO<>(getPatientUseCase.handler(user, id)));
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

    @PostMapping("/page/{page}")
    public ResponseEntity<?> page(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable Integer page,
            @Valid @RequestBody GetPagePatientRequest request
    ) {
        var user = userAuthentication.getUser();

        var result = getPagePatientUseCase.handler(user, page, request);

        return ResponseEntity.ok(new ResponseDTO<>(result));
    }
}