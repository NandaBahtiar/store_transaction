package com.HCS_IDN.HCS_IDN;

import com.HCS_IDN.HCS_IDN.dtos.CustomerSpendingReportDto;
import com.HCS_IDN.HCS_IDN.dtos.ProductPerformanceReportDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionDto;
import com.HCS_IDN.HCS_IDN.models.Customer;
import com.HCS_IDN.HCS_IDN.models.Product;
import com.HCS_IDN.HCS_IDN.models.Transaction;
import com.HCS_IDN.HCS_IDN.models.TransactionDetail;
import com.HCS_IDN.HCS_IDN.models.User;
import com.HCS_IDN.HCS_IDN.repositories.CustomerRepository;
import com.HCS_IDN.HCS_IDN.repositories.TransactionRepository;
import com.HCS_IDN.HCS_IDN.services.ReportService;
import com.HCS_IDN.HCS_IDN.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private ReportService reportService;

    private User staffUser;
    private Customer customer;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        staffUser = new User(1L, "staff", "staff@example.com", User.Role.STAFF, LocalDateTime.now(), LocalDateTime.now());
        customer = new Customer(1L, "John Doe", LocalDate.of(1990, 5, 15), "New York", staffUser, staffUser, LocalDateTime.now(), LocalDateTime.now());
        product1 = new Product(1L, "Laptop", BigDecimal.valueOf(1000.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        product2 = new Product(2L, "Mouse", BigDecimal.valueOf(25.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
    }

    @Test
    void getCustomerSpendingReport() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTotalAmount(BigDecimal.valueOf(1200.00));
        transactionDto.setCustomerId(customer.getId());

        when(transactionService.searchTransactions(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(transactionDto)));

        CustomerSpendingReportDto report = reportService.getCustomerSpendingReport(1L, LocalDate.now().minusDays(7), LocalDate.now());

        assertNotNull(report);
        assertEquals(customer.getId(), report.getCustomerId());
        assertEquals(BigDecimal.valueOf(1200.00), report.getTotalSpent());
        assertEquals(1L, report.getTransactionCount());
    }

    @Test
    void getCustomerTotalSpendingAllTime() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTotalAmount(BigDecimal.valueOf(2500.00));
        transactionDto.setCustomerId(customer.getId());

        when(transactionService.searchTransactions(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(transactionDto)));

        CustomerSpendingReportDto report = reportService.getCustomerTotalSpendingAllTime(1L);

        assertNotNull(report);
        assertEquals(customer.getId(), report.getCustomerId());
        assertEquals(BigDecimal.valueOf(2500.00), report.getTotalSpent());
        assertEquals(1L, report.getTransactionCount());
        assertEquals("All Time", report.getDateRange());
    }

    @Test
    void getProductPerformanceReport() {
        Transaction transaction = new Transaction(1L, customer, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), Transaction.PaymentStatus.PAID, Transaction.PaymentMethod.CARD, staffUser, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        TransactionDetail detail1 = new TransactionDetail(1L, transaction, product1, 1, BigDecimal.valueOf(1000.00), BigDecimal.valueOf(1000.00), BigDecimal.valueOf(100.00));
        TransactionDetail detail2 = new TransactionDetail(2L, transaction, product2, 2, BigDecimal.valueOf(25.00), BigDecimal.valueOf(50.00), BigDecimal.valueOf(5.00));
        transaction.setTransactionDetails(new HashSet<>(Arrays.asList(detail1, detail2)));

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));

        List<ProductPerformanceReportDto> reports = reportService.getProductPerformanceReport();

        assertNotNull(reports);
        assertEquals(2, reports.size());

        ProductPerformanceReportDto report1 = reports.stream().filter(r -> r.getProductId().equals(product1.getId())).findFirst().orElse(null);
        assertNotNull(report1);
        assertEquals(BigDecimal.valueOf(1100.00), report1.getTotalRevenue());
        assertEquals(1L, report1.getTransactionCount());

        ProductPerformanceReportDto report2 = reports.stream().filter(r -> r.getProductId().equals(product2.getId())).findFirst().orElse(null);
        assertNotNull(report2);
        assertEquals(BigDecimal.valueOf(55.00), report2.getTotalRevenue());
        assertEquals(1L, report2.getTransactionCount());
    }

    @Test
    void getCustomerTransactionHistory() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setCustomerId(customer.getId());

        when(transactionService.searchTransactions(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(transactionDto)));

        List<TransactionDto> history = reportService.getCustomerTransactionHistory(1L);

        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals(customer.getId(), history.get(0).getCustomerId());
    }
}
