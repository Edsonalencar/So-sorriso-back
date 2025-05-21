package br.com.sorriso.application.api.local;

import br.com.sorriso.application.api.common.GetPageRequest;
import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.local.dtos.CreateLocalStockRequest;
import br.com.sorriso.application.api.local.dtos.UpdateLocalStatusRequest;
import br.com.sorriso.application.useCase.local.*;
import br.com.sorriso.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sorriso/api/local")
@RequiredArgsConstructor
public class LocalController {

    private final CreateLocalUserCase createLocalUserCase;
    private final UpdateLocalUseCase updateLocalUseCase;
    private final GetPageLocalUseCase getPageLocalUseCase;
    private final GetAllILocalUseCase getAllILocalUseCase;
    private final UpdateStatusLocalUseCase updateStatusLocalUseCase;

    @PostMapping
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody CreateLocalStockRequest request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                createLocalUserCase.handler(user,request)
            )
        );
    }

    @PutMapping("/{localId}")
    public ResponseEntity<?> update(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody CreateLocalStockRequest request,
        @PathVariable UUID localId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateLocalUseCase.handler(user, localId, request)
            )
        );
    }

    @PutMapping("/{localId}/status")
    public ResponseEntity<?> updateStatus(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody UpdateLocalStatusRequest request,
        @PathVariable UUID localId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateStatusLocalUseCase.handler(user, localId, request)
            )
        );
    }

    @PostMapping("/page/{page}")
    public ResponseEntity<?> page(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable Integer page,
        @RequestBody GetPageRequest request
    ){
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getPageLocalUseCase.handler(user, page, request)
            )
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
        @AuthenticationPrincipal CustomUserDetails userAuthentication
    ){
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getAllILocalUseCase.handler(user)
            )
        );
    }

}
