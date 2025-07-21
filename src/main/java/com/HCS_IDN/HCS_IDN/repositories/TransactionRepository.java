package com.HCS_IDN.HCS_IDN.repositories;

import com.HCS_IDN.HCS_IDN.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE " +
           "(:startDate IS NULL OR t.transactionTime >= :startDate) AND " +
           "(:endDate IS NULL OR t.transactionTime <= :endDate) AND " +
           "(:customerName IS NULL OR LOWER(t.customer.name) LIKE LOWER(CONCAT('%', :customerName, '%'))) AND " +
           "(:paymentStatuses IS NULL OR t.paymentStatus IN :paymentStatuses) AND " +
           "(:paymentMethods IS NULL OR t.paymentMethod IN :paymentMethods) AND " +
           "(:staffId IS NULL OR t.staff.id = :staffId)")
    Page<Transaction> findTransactionsByFilters(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("customerName") String customerName,
            @Param("paymentStatuses") List<Transaction.PaymentStatus> paymentStatuses,
            @Param("paymentMethods") List<Transaction.PaymentMethod> paymentMethods,
            @Param("staffId") Long staffId,
            Pageable pageable);
}