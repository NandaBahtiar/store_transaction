package com.HCS_IDN.HCS_IDN.repositories;

import com.HCS_IDN.HCS_IDN.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
