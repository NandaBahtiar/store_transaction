package com.HCS_IDN.HCS_IDN.dtos;

import com.HCS_IDN.HCS_IDN.models.Transaction;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class TransactionFilterDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String customerName;
    private List<Transaction.PaymentStatus> paymentStatus;
    private List<Transaction.PaymentMethod> paymentMethod;
    private Long staffId;
    private Long customerId;
    private String sortBy = "transactionTime";
    private String sortOrder = "DESC";
}