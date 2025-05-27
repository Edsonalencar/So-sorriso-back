package br.com.sorriso.application.api.patient.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRegistrationRequest {
    private String photo;
    private String document;
    private String name;
    private String phone;
    private LocalDate birthDate;
}
