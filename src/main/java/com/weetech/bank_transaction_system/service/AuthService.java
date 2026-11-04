package com.weetech.bank_transaction_system.service;

import com.weetech.bank_transaction_system.dto.LoginRequestDTO;
import com.weetech.bank_transaction_system.dto.LoginResponseDTO;
import com.weetech.bank_transaction_system.dto.RegisterRequestDTO;
import com.weetech.bank_transaction_system.entity.Users;
import com.weetech.bank_transaction_system.enums.Role;
import com.weetech.bank_transaction_system.exception.EmailAlreadyExistsException;
import com.weetech.bank_transaction_system.exception.InvalidCredentialsException;
import com.weetech.bank_transaction_system.repository.UserRepository;
import com.weetech.bank_transaction_system.specification.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // Password Hash
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);


    // Register User
    public void registerUser(RegisterRequestDTO registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered. Please use another email.");
        }

        Users user = new Users();

        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());

        user.setPassword(
                passwordEncoder.encode(registerRequest.getPassword())
        );

        user.setRole(Role.USER);

        userRepository.save(user);
    }

    // Login Verify User
    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) throws InvalidCredentialsException {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserPrincipal userPrincipal =
                    (UserPrincipal) authentication.getPrincipal();

            String accessToken = jwtService.generateToken(
                    userPrincipal.getUsername()
            );

            return new LoginResponseDTO(
                    accessToken,
                    "Bearer",
                    "Login successful"
            );

        } catch (AuthenticationException ex) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }
    }



}
