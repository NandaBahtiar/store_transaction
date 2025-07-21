package com.HCS_IDN.HCS_IDN.dtos;

import com.HCS_IDN.HCS_IDN.models.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TransactionCreateDto {
    @NotNull
    private Long customerId;

    @NotNull
    private Long staffId;

    @NotNull
    private Transaction.PaymentMethod paymentMethod;

    private List<TransactionDetailCreateDto> transactionDetails;
}
