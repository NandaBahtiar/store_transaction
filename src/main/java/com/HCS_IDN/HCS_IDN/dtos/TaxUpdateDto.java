package com.HCS_IDN.HCS_IDN.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxUpdateDto {
    private String name;
    private BigDecimal percentage;
}
