package com.weetech.bank_transaction_system.service;

import com.weetech.bank_transaction_system.dto.ChangePasswordRequestDTO;
import com.weetech.bank_transaction_system.dto.ProfileUpdateRequestDTO;
import com.weetech.bank_transaction_system.dto.ProfileUpdateResponseDTO;
import com.weetech.bank_transaction_system.dto.UserProfileResponseDTO;
import com.weetech.bank_transaction_system.entity.Users;
import com.weetech.bank_transaction_system.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // Get user's profile
    public Optional<UserProfileResponseDTO> getUserProfile(String email) {

        return userRepository.findByEmail(email)
                .map(user -> new UserProfileResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getBalance(),
                        user.getTotalIncome(),
                        user.getTotalExpenses(),
                        user.getCreatedDate()
                ));
    }


    public ProfileUpdateResponseDTO updateProfile(
            Authentication authentication,
            ProfileUpdateRequestDTO requestDTO
    ) {

        Users user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(requestDTO.getName());

        Users updatedUser = userRepository.save(user);

        return new ProfileUpdateResponseDTO(
                "Profile updated successfully",
                updatedUser.getName()
        );
    }


    public void changePassword(
            Authentication authentication,
            ChangePasswordRequestDTO requestDTO
    ) {

        Users user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                requestDTO.getCurrentPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(requestDTO.getNewPassword())
        );

        userRepository.save(user);
    }


    public void logoutUser() {
        SecurityContextHolder.clearContext();
    }

}