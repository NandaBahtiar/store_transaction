package com.HCS_IDN.HCS_IDN.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerSpendingReportDto {
    private Long customerId;
    private String customerName;
    private BigDecimal totalSpent;
    private Long transactionCount;
    private String dateRange;
}
