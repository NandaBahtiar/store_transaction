package com.HCS_IDN.HCS_IDN.services;

import com.HCS_IDN.HCS_IDN.dtos.*;
import com.HCS_IDN.HCS_IDN.models.*;
import com.HCS_IDN.HCS_IDN.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductTaxRepository productTaxRepository;

    public TransactionService(TransactionRepository transactionRepository, TransactionDetailRepository transactionDetailRepository, CustomerRepository customerRepository, UserRepository userRepository, ProductRepository productRepository, ProductTaxRepository productTaxRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionDetailRepository = transactionDetailRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productTaxRepository = productTaxRepository;
    }

    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public TransactionDto getTransactionById(Long id) {
        return toDto(transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found")));
    }

    public TransactionDto createTransaction(TransactionCreateDto transactionCreateDto) {
        Customer customer = customerRepository.findById(transactionCreateDto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        User staff = userRepository.findById(transactionCreateDto.getStaffId()).orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setStaff(staff);
        transaction.setPaymentMethod(transactionCreateDto.getPaymentMethod());
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setPaymentStatus(Transaction.PaymentStatus.NOT_PAID);

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;

        Transaction savedTransaction = transactionRepository.save(transaction);

        for (TransactionDetailCreateDto detailDto : transactionCreateDto.getTransactionDetails()) {
            Product product = productRepository.findById(detailDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            BigDecimal unitPrice = product.getBasePrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(detailDto.getQuantity()));
            BigDecimal taxAmount = calculateTax(product, subtotal);

            TransactionDetail detail = new TransactionDetail();
            detail.setTransaction(savedTransaction);
            detail.setProduct(product);
            detail.setQuantity(detailDto.getQuantity());
            detail.setUnitPrice(unitPrice);
            detail.setSubtotal(subtotal);
            detail.setTaxAmount(taxAmount);
            transactionDetailRepository.save(detail);

            totalAmount = totalAmount.add(subtotal).add(taxAmount);
            totalTax = totalTax.add(taxAmount);
        }

        savedTransaction.setTotalAmount(totalAmount);
        savedTransaction.setTotalTax(totalTax);
        savedTransaction.setNetAmount(totalAmount.subtract(totalTax));

        return toDto(transactionRepository.save(savedTransaction));
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public TransactionDto updatePaymentStatus(Long id, Transaction.PaymentStatus status) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setPaymentStatus(status);
        return toDto(transactionRepository.save(transaction));
    }

    public Page<TransactionDto> searchTransactions(TransactionFilterDto filterDto, Pageable pageable) {
        return transactionRepository.findTransactionsByFilters(
                filterDto.getStartDate() != null ? filterDto.getStartDate().atStartOfDay() : null,
                filterDto.getEndDate() != null ? filterDto.getEndDate().atTime(23, 59, 59, 999999999) : null,
                filterDto.getCustomerName(),
                filterDto.getPaymentStatus(),
                filterDto.getPaymentMethod(),
                filterDto.getStaffId(),
                pageable
        ).map(this::toDto);
    }

    private BigDecimal calculateTax(Product product, BigDecimal subtotal) {
        BigDecimal taxPercentage = productTaxRepository.findAll().stream()
                .filter(pt -> pt.getProduct().getId().equals(product.getId()))
                .map(pt -> pt.getTax().getPercentage())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return subtotal.multiply(taxPercentage.divide(BigDecimal.valueOf(100)));
    }

    private TransactionDto toDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setCustomerId(transaction.getCustomer().getId());
        dto.setNetAmount(transaction.getNetAmount());
        dto.setTotalAmount(transaction.getTotalAmount());
        dto.setTotalTax(transaction.getTotalTax());
        dto.setTransactionTime(transaction.getTransactionTime());
        dto.setPaymentStatus(transaction.getPaymentStatus());
        dto.setPaymentMethod(transaction.getPaymentMethod());
        dto.setStaffId(transaction.getStaff().getId());
        dto.setTransactionDetails(transactionDetailRepository.findAll().stream()
                .filter(td -> td.getTransaction().getId().equals(transaction.getId()))
                .map(this::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private TransactionDetailDto toDto(TransactionDetail transactionDetail) {
        TransactionDetailDto dto = new TransactionDetailDto();
        dto.setId(transactionDetail.getId());
        dto.setProductId(transactionDetail.getProduct().getId());
        dto.setQuantity(transactionDetail.getQuantity());
        dto.setUnitPrice(transactionDetail.getUnitPrice());
        dto.setSubtotal(transactionDetail.getSubtotal());
        dto.setTaxAmount(transactionDetail.getTaxAmount());
        return dto;
    }
}