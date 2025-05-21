package br.com.sorriso.application.api.clinic;

import br.com.sorriso.application.api.common.GetPageRequest;
import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.user.dto.UserRegistrationRequest;
import br.com.sorriso.application.useCase.clinic.CreateCliniicUseCase;
import br.com.sorriso.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import br.com.sorriso.application.useCase.clinic.*;

import java.util.UUID;

@RestController
@RequestMapping("/sorriso/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final CreateCliniicUseCase createCliniicUseCase;
    private final GetPageClinicUseCase getPageClinicUseCase;
    private final GetClinicUseCase getClinicUseCase;
    private final UpdateClinicUseCase updateClinicUseCase;
    private final GetAllClinicsUseCase getAllClinicsByUserUseCase;

    @PostMapping
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @RequestBody UserRegistrationRequest request
    ) {

        return ResponseEntity.ok(
            new ResponseDTO<>(
                createCliniicUseCase.handler(request)
            )
        );
    }


    @PutMapping("/{clinicId}")
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody UserRegistrationRequest request,
        @PathVariable UUID clinicId
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateClinicUseCase.handler(clinicId, request)
            )
        );
    }

    @PostMapping("/page/{page}")
    public ResponseEntity<?> page(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable Integer page,
        @RequestBody GetPageRequest request
    ){
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getPageClinicUseCase.handler(page, request)
            )
        );
    }

    @GetMapping("/{clinicId}")
    public ResponseEntity<?> get(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable UUID clinicId
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getClinicUseCase.handler(clinicId)
            )
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllClinicsByUser(
        @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getAllClinicsByUserUseCase.handler(
                    userAuthentication.getUser().getId()
                )
            )
        );
    }
}
