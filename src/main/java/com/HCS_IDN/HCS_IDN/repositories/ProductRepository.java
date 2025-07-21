package com.HCS_IDN.HCS_IDN.repositories;

import com.HCS_IDN.HCS_IDN.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
