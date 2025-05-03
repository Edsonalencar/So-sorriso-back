package br.com.starter.application.api.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO {
    String street;
    String number;
    String cep;
    String province;
    String city;
    String state;
}