package com.HCS_IDN.HCS_IDN.dtos;

import com.HCS_IDN.HCS_IDN.models.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private User.Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
