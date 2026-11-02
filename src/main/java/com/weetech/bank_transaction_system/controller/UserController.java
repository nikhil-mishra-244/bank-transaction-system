package com.weetech.bank_transaction_system.controller;

import com.weetech.bank_transaction_system.entity.Users;
import com.weetech.bank_transaction_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
