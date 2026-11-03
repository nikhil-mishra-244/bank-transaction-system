package com.weetech.bank_transaction_system.controller;

import com.weetech.bank_transaction_system.dto.LoginRequestDTO;
import com.weetech.bank_transaction_system.dto.LoginResponseDTO;
import com.weetech.bank_transaction_system.entity.Users;
import com.weetech.bank_transaction_system.service.UserService;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody Users user) {

        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.loginUser(requestDTO)
        );
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUser());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        return userService.fetchById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}
