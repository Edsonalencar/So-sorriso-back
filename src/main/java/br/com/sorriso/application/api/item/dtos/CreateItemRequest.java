package br.com.sorriso.application.api.item.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateItemRequest {
    private String name;
    private String description;
    private String code;
    private UUID vehicleTypeId = null;
}
