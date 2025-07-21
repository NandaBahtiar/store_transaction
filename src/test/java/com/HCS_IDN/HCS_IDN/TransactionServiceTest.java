package com.HCS_IDN.HCS_IDN;

import com.HCS_IDN.HCS_IDN.dtos.TransactionCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionDetailCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionDto;
import com.HCS_IDN.HCS_IDN.dtos.TransactionFilterDto;
import com.HCS_IDN.HCS_IDN.models.*;
import com.HCS_IDN.HCS_IDN.repositories.*;
import com.HCS_IDN.HCS_IDN.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionDetailRepository transactionDetailRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductTaxRepository productTaxRepository;

    @InjectMocks
    private TransactionService transactionService;

    private User staffUser;
    private Customer customer;
    private Product product;
    private Tax tax;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        staffUser = new User(1L, "staff", "staff@example.com", "password123", User.Role.STAFF, LocalDateTime.now(), LocalDateTime.now());
        customer = new Customer(1L, "John Doe", LocalDate.of(1990, 5, 15), "New York", staffUser, staffUser, LocalDateTime.now(), LocalDateTime.now());
        product = new Product(1L, "Laptop", BigDecimal.valueOf(1000.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        tax = new Tax(1L, "VAT", BigDecimal.valueOf(10.00), new HashSet<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(staffUser));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productTaxRepository.findAll()).thenReturn(Collections.singletonList(new ProductTax(1L, product, tax)));
    }

    @Test
    void getAllTransactions() {
        Transaction transaction = new Transaction(1L, customer, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), Transaction.PaymentStatus.NOT_PAID, Transaction.PaymentMethod.CASH, staffUser, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction));
        when(transactionDetailRepository.findAll()).thenReturn(Collections.emptyList());

        List<TransactionDto> transactions = transactionService.getAllTransactions();

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals(customer.getId(), transactions.get(0).getCustomerId());
    }

    @Test
    void getTransactionById() {
        Transaction transaction = new Transaction(1L, customer, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), Transaction.PaymentStatus.NOT_PAID, Transaction.PaymentMethod.CASH, staffUser, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionDetailRepository.findAll()).thenReturn(Collections.emptyList());

        TransactionDto foundTransaction = transactionService.getTransactionById(1L);

        assertNotNull(foundTransaction);
        assertEquals(customer.getId(), foundTransaction.getCustomerId());
    }

    @Test
    void getTransactionByIdNotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.getTransactionById(1L));
    }

    @Test
    void createTransaction() {
        TransactionCreateDto createDto = new TransactionCreateDto();
        createDto.setCustomerId(customer.getId());
        createDto.setStaffId(staffUser.getId());
        createDto.setPaymentMethod(Transaction.PaymentMethod.CARD);

        TransactionDetailCreateDto detailDto = new TransactionDetailCreateDto();
        detailDto.setProductId(product.getId());
        detailDto.setQuantity(2);
        createDto.setTransactionDetails(Collections.singletonList(detailDto));

        Transaction savedTransaction = new Transaction(1L, customer, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), Transaction.PaymentStatus.NOT_PAID, Transaction.PaymentMethod.CARD, staffUser, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);
        when(transactionDetailRepository.save(any(TransactionDetail.class))).thenReturn(new TransactionDetail());

        TransactionDto createdTransaction = transactionService.createTransaction(createDto);

        assertNotNull(createdTransaction);
        assertEquals(customer.getId(), createdTransaction.getCustomerId());
        assertTrue(createdTransaction.getTotalAmount().compareTo(BigDecimal.ZERO) > 0);
        verify(transactionRepository, times(2)).save(any(Transaction.class)); // Initial save and final update
        verify(transactionDetailRepository, times(1)).save(any(TransactionDetail.class));
    }

    @Test
    void deleteTransaction() {
        doNothing().when(transactionRepository).deleteById(1L);

        transactionService.deleteTransaction(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void updatePaymentStatus() {
        Transaction existingTransaction = new Transaction(1L, customer, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), Transaction.PaymentStatus.NOT_PAID, Transaction.PaymentMethod.CASH, staffUser, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(existingTransaction);

        TransactionDto updatedTransaction = transactionService.updatePaymentStatus(1L, Transaction.PaymentStatus.PAID);

        assertNotNull(updatedTransaction);
        assertEquals(Transaction.PaymentStatus.PAID, updatedTransaction.getPaymentStatus());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void searchTransactions() {
        Transaction transaction = new Transaction(1L, customer, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), Transaction.PaymentStatus.PAID, Transaction.PaymentMethod.CARD, staffUser, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Transaction> transactionPage = new PageImpl<>(Arrays.asList(transaction), pageable, 1);

        when(transactionRepository.findTransactionsByFilters(any(), any(), any(), any(), any(), any(), any())).thenReturn(transactionPage);
        when(transactionDetailRepository.findAll()).thenReturn(Collections.emptyList());

        TransactionFilterDto filterDto = new TransactionFilterDto();
        filterDto.setPaymentStatus(Collections.singletonList(Transaction.PaymentStatus.PAID));

        Page<TransactionDto> result = transactionService.searchTransactions(filterDto, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(Transaction.PaymentStatus.PAID, result.getContent().get(0).getPaymentStatus());
    }
}