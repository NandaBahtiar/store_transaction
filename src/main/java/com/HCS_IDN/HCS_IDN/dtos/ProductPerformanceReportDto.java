package com.HCS_IDN.HCS_IDN.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPerformanceReportDto {
    private Long productId;
    private String productName;
    private BigDecimal totalRevenue = BigDecimal.ZERO;
    private Long transactionCount = 0L;
}