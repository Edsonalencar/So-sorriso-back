package br.com.sorriso.application.api.local.dtos;

import lombok.Data;

@Data
public class CreateLocalStockRequest {
    private String name;
    private String description;
}
