package com.HCS_IDN.HCS_IDN.repositories;

import com.HCS_IDN.HCS_IDN.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
