package com.HCS_IDN.HCS_IDN.repositories;

import com.HCS_IDN.HCS_IDN.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
