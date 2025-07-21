package com.HCS_IDN.HCS_IDN.controllers;

import com.HCS_IDN.HCS_IDN.dtos.TransactionCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionFilterDto;
import com.HCS_IDN.HCS_IDN.models.Transaction;
import com.HCS_IDN.HCS_IDN.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(@Valid @RequestBody TransactionCreateDto transactionCreateDto) {
        return transactionService.createTransaction(transactionCreateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @PutMapping("/{id}/status")
    public TransactionDto updatePaymentStatus(@PathVariable Long id, @RequestBody Transaction.PaymentStatus status) {
        return transactionService.updatePaymentStatus(id, status);
    }

    @GetMapping("/filter")
    public Page<TransactionDto> filterTransactions(TransactionFilterDto filterDto, Pageable pageable) {
        return transactionService.searchTransactions(filterDto, pageable);
    }
}