package br.com.sorriso.application.api.item;

import br.com.sorriso.application.api.common.GetPageRequest;
import br.com.sorriso.application.api.common.ResponseDTO;
import br.com.sorriso.application.api.item.dtos.CreateItemRequest;
import br.com.sorriso.application.api.item.dtos.UpdateItemStatusRequest;
import br.com.sorriso.application.useCase.item.*;
import br.com.sorriso.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sorriso/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final UpdateStatusItemUseCase updateStatusItemUseCase;
    private final GetPageItemUseCase getPageItemUseCase;
    private final GetAllItemUseCase getAllItemUseCase;

    @PostMapping
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody CreateItemRequest request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                createItemUseCase.handler(user,request)
            )
        );
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody CreateItemRequest request,
        @PathVariable UUID itemId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateItemUseCase.handler(user, itemId, request)
            )
        );
    }

    @PutMapping("/{itemId}/status")
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody UpdateItemStatusRequest request,
        @PathVariable UUID itemId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateStatusItemUseCase.handler(user, itemId, request)
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
                getPageItemUseCase.handler(user, page, request)
            )
        );
    }

    @GetMapping
    public ResponseEntity<?> page(
        @AuthenticationPrincipal CustomUserDetails userAuthentication
    ){
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getAllItemUseCase.handler(user)
            )
        );
    }
}
