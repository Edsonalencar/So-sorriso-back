package br.com.sorriso.application.api.patient.dto;

import br.com.sorriso.domain.address.Address;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRegistrationRequest {
    private String photo;
    private String document;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private Address address;

}
