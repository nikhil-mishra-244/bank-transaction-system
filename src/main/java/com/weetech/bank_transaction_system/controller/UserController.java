package com.weetech.bank_transaction_system.controller;

import com.weetech.bank_transaction_system.dto.ChangePasswordRequestDTO;
import com.weetech.bank_transaction_system.dto.ProfileUpdateRequestDTO;
import com.weetech.bank_transaction_system.dto.ProfileUpdateResponseDTO;
import com.weetech.bank_transaction_system.dto.UserProfileResponseDTO;
import com.weetech.bank_transaction_system.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(
            Authentication authentication) {

        String email = authentication.getName();

        return userService.getUserProfile(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/profile")
    public ResponseEntity<ProfileUpdateResponseDTO> updateProfile(
            Authentication authentication,
            @Valid @RequestBody ProfileUpdateRequestDTO requestDTO
    ) {

        return ResponseEntity.ok(
                userService.updateProfile(authentication, requestDTO)
        );
    }


    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequestDTO requestDTO
    ) {

        userService.changePassword(authentication, requestDTO);

        return ResponseEntity.ok("Password changed successfully");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok(
                "Logout successful"
        );
    }


}
