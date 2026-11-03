package com.weetech.bank_transaction_system.service;

import com.weetech.bank_transaction_system.dto.LoginRequestDTO;
import com.weetech.bank_transaction_system.dto.LoginResponseDTO;
import com.weetech.bank_transaction_system.entity.Users;
import com.weetech.bank_transaction_system.enums.Role;
import com.weetech.bank_transaction_system.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import com.weetech.bank_transaction_system.specification.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // Password Hash
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);


    // Create New User
    public void createUser(Users user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists!");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    // Login Verify User
    public LoginResponseDTO loginUser(LoginRequestDTO userLogin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));

        UserPrincipal userPrincipal =
                (UserPrincipal) authentication.getPrincipal();

        String token = jwtService.generateToken(
                userPrincipal.getUsername()
        );

        return new LoginResponseDTO(
                token,
                "Bearer",
                userPrincipal.getUsername()
        );
    }

    // Remove User
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    // Get All User
    public List<Users> fetchAllUser() {
        return userRepository.findAll();
    }

    // Get One User By ID
    public Optional<Users> fetchById(long id) {
        return userRepository.findById(id);
    }
}
