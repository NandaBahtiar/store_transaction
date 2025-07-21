package com.HCS_IDN.HCS_IDN.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxDto {
    private Long id;
    private String name;
    private BigDecimal percentage;
}
