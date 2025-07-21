package com.HCS_IDN.HCS_IDN.services;

import com.HCS_IDN.HCS_IDN.dtos.CustomerSpendingReportDto;
import com.HCS_IDN.HCS_IDN.dtos.ProductPerformanceReportDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionDto;
import com.HCS_IDN.HCS_IDN.models.Customer;
import com.HCS_IDN.HCS_IDN.repositories.CustomerRepository;
import com.HCS_IDN.HCS_IDN.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.HCS_IDN.HCS_IDN.models.TransactionDetail;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;

@Service
public class ReportService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;

    public ReportService(TransactionRepository transactionRepository, CustomerRepository customerRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.transactionService = transactionService;
    }

    public CustomerSpendingReportDto getCustomerSpendingReport(Long customerId, LocalDate startDate, LocalDate endDate) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        List<TransactionDto> transactions = transactionService.searchTransactions(
                new com.HCS_IDN.HCS_IDN.dtos.TransactionFilterDto() {{
                    setCustomerId(customerId);
                    setStartDate(startDate);
                    setEndDate(endDate);
                }},
                Pageable.unpaged()
        ).getContent();

        BigDecimal totalSpent = transactions.stream()
                .map(TransactionDto::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CustomerSpendingReportDto report = new CustomerSpendingReportDto();
        report.setCustomerId(customerId);
        report.setCustomerName(customer.getName());
        report.setTotalSpent(totalSpent);
        report.setTransactionCount((long) transactions.size());
        report.setDateRange(startDate + " to " + endDate);
        return report;
    }

    public CustomerSpendingReportDto getCustomerTotalSpendingAllTime(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        List<TransactionDto> transactions = transactionService.searchTransactions(
                new com.HCS_IDN.HCS_IDN.dtos.TransactionFilterDto() {{
                    setCustomerId(customerId);
                }},
                Pageable.unpaged()
        ).getContent();

        BigDecimal totalSpent = transactions.stream()
                .map(TransactionDto::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CustomerSpendingReportDto report = new CustomerSpendingReportDto();
        report.setCustomerId(customerId);
        report.setCustomerName(customer.getName());
        report.setTotalSpent(totalSpent);
        report.setTransactionCount((long) transactions.size());
        report.setDateRange("All Time");
        return report;
    }

    public List<ProductPerformanceReportDto> getProductPerformanceReport() {
        // This will require a custom query in TransactionDetailRepository or a more complex service method
        // For now, a placeholder implementation
        return transactionRepository.findAll().stream()
                .flatMap(transaction -> transaction.getTransactionDetails().stream())
                .collect(Collectors.toMap(
                        detail -> detail.getProduct().getId(),
                        detail -> {
                            ProductPerformanceReportDto dto = new ProductPerformanceReportDto();
                            dto.setProductId(detail.getProduct().getId());
                            dto.setProductName(detail.getProduct().getName());
                            dto.setTotalRevenue(detail.getSubtotal().add(detail.getTaxAmount()));
                            dto.setTransactionCount(1L);
                            return dto;
                        },
                        (dto1, dto2) -> {
                            dto1.setTotalRevenue(dto1.getTotalRevenue().add(dto2.getTotalRevenue()));
                            dto1.setTransactionCount(dto1.getTransactionCount() + dto2.getTransactionCount());
                            return dto1;
                        }
                ))
                .values().stream()
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getCustomerTransactionHistory(Long customerId) {
        return transactionService.searchTransactions(
                new com.HCS_IDN.HCS_IDN.dtos.TransactionFilterDto() {{
                    setCustomerId(customerId);
                }},
                Pageable.unpaged()
        ).getContent();
    }
}
