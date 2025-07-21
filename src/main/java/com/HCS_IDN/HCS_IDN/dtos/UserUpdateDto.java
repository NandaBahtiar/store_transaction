package com.HCS_IDN.HCS_IDN.dtos;

import com.HCS_IDN.HCS_IDN.models.User;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;

    @Email
    private String email;

    private String password;

    private User.Role role;
}
