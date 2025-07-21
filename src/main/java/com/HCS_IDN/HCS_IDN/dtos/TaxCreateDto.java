package com.HCS_IDN.HCS_IDN.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxCreateDto {
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal percentage;
}
