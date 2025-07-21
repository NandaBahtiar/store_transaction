package com.HCS_IDN.HCS_IDN.dtos;

import com.HCS_IDN.HCS_IDN.models.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransactionDto {
    private Long id;
    private Long customerId;
    private BigDecimal netAmount;
    private BigDecimal totalAmount;
    private BigDecimal totalTax;
    private LocalDateTime transactionTime;
    private Transaction.PaymentStatus paymentStatus;
    private Transaction.PaymentMethod paymentMethod;
    private Long staffId;
    private List<TransactionDetailDto> transactionDetails;
}
