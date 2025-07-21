package com.HCS_IDN.HCS_IDN.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDetailDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
}
