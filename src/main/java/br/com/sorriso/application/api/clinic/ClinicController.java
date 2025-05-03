package br.com.sorriso.application.api.clinic;

import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.user.dto.UserRegistrationRequest;
import br.com.sorriso.application.useCases.clinic.CreateCliniicUseCase;
import br.com.sorriso.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sorriso/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final CreateCliniicUseCase createCliniicUseCase;

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
}
