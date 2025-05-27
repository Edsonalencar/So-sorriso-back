package br.com.sorriso.application.useCase.patient;

import br.com.sorriso.application.api.patient.dtos.GetPagePatientRequest;
import br.com.sorriso.domain.clinic.ClinicService;
import br.com.sorriso.domain.patient.PatientService;
import br.com.sorriso.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPagePatientUseCase {
    private final PatientService patientService;
    private final ClinicService clinicService;

    public Optional<?> handler(
        User user,
        Integer page,
        GetPagePatientRequest request
    ) {
        var clinic = clinicService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Clinic not found"
                )
        );

        var ids = patientService.getPageFilterIds(clinic, request);

        var pageRequest = PageRequest.of(page, request.getSize());

        var contents = (ids != null && !ids.isEmpty())
                ? patientService.getAllByIds(clinic.getId(), ids, pageRequest)
                : patientService.getAllByClinicId(clinic.getId(), pageRequest);

        return Optional.of(contents);
    }
}