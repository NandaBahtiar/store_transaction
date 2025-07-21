package com.HCS_IDN.HCS_IDN.services;

import com.HCS_IDN.HCS_IDN.dtos.LoginRequest;
import com.HCS_IDN.HCS_IDN.dtos.LoginResponse;
import com.HCS_IDN.HCS_IDN.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthService(AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // For now, we'll just return a dummy token. In a real application, you'd generate a JWT.
        return new LoginResponse("dummy-jwt-token");
    }
}
