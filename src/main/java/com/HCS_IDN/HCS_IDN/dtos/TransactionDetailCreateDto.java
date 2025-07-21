package com.HCS_IDN.HCS_IDN.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionDetailCreateDto {
    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;
}
