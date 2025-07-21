package com.HCS_IDN.HCS_IDN.controllers;

import com.HCS_IDN.HCS_IDN.dtos.LoginRequest;
import com.HCS_IDN.HCS_IDN.dtos.LoginResponse;
import com.HCS_IDN.HCS_IDN.dtos.UserCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.UserDto;
import com.HCS_IDN.HCS_IDN.services.AuthService;
import com.HCS_IDN.HCS_IDN.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto newUser = userService.createUser(userCreateDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
