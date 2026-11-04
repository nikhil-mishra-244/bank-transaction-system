package com.weetech.bank_transaction_system.controller;

import com.weetech.bank_transaction_system.dto.LoginRequestDTO;
import com.weetech.bank_transaction_system.dto.LoginResponseDTO;
import com.weetech.bank_transaction_system.dto.RegisterRequestDTO;
import com.weetech.bank_transaction_system.dto.RegisterResponseDTO;
import com.weetech.bank_transaction_system.exception.InvalidCredentialsException;
import com.weetech.bank_transaction_system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    // Register new user
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest) {

        authService.registerUser(registerRequest);
        RegisterResponseDTO response = new RegisterResponseDTO("User registered successfully",true);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) throws InvalidCredentialsException {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest)
        );
    }


}
