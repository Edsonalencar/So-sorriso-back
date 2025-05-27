package br.com.sorriso.application.api.patient.dtos;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GetPagePatientRequest {
    private Integer size = 10;
    private Set<UUID> patientIds;
    private String name;
    private String document;
}
