package br.com.sorriso.application.useCase.clinic;

import br.com.sorriso.application.api.common.GetPageRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageClinicUseCase {

    private final ClinicService clinicService;

    public Optional<?> handler(Integer page, GetPageRequest request) {
        return Optional.of(
            clinicService.getPageByOwnerStatusAndName(
                request.getQuery(),
                request.getStatus(),
                PageRequest.of(page, request.getSize())
            )
        );
    }
}
