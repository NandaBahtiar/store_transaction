package com.HCS_IDN.HCS_IDN.controllers;

import com.HCS_IDN.HCS_IDN.dtos.CustomerSpendingReportDto;
import com.HCS_IDN.HCS_IDN.dtos.ProductPerformanceReportDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionDto;
import com.HCS_IDN.HCS_IDN.services.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/customer/{customerId}/spending")
    public CustomerSpendingReportDto getCustomerSpendingReport(
            @PathVariable Long customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reportService.getCustomerSpendingReport(customerId, startDate, endDate);
    }

    @GetMapping("/customer/{customerId}/spending/alltime")
    public CustomerSpendingReportDto getCustomerTotalSpendingAllTime(@PathVariable Long customerId) {
        return reportService.getCustomerTotalSpendingAllTime(customerId);
    }

    @GetMapping("/products/performance")
    public List<ProductPerformanceReportDto> getProductPerformanceReport() {
        return reportService.getProductPerformanceReport();
    }

    @GetMapping("/customer/{customerId}/transactions")
    public List<TransactionDto> getCustomerTransactionHistory(@PathVariable Long customerId) {
        return reportService.getCustomerTransactionHistory(customerId);
    }
}
