
package com.example.hms.service;

import com.example.hms.dto.RegisterRequest;
import com.example.hms.dto.RegisterResponse;
import com.example.hms.entity.Role;
import com.example.hms.entity.User;
import com.example.hms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.hms.dto.LoginRequest;
import com.example.hms.dto.LoginResponse;
import com.example.hms.config.JwtService;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication - Service
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Handles user registration business logic.
 * Encrypts passwords and saves new users in the database.
 */

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Inject repository and password encoder
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Register a new user
    public RegisterResponse register(RegisterRequest request) {

        // Prevent registration with an already existing email
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Encrypt the password before saving it
        String encodedPassword = passwordEncoder.encode(request.password());

        // Create a new user with the default USER role
        User user = User.builder()
                .name(request.name().trim())
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        // Save the registered user in the database
        User savedUser = userRepository.save(user);

        // Return the registered user's information
        return new RegisterResponse(
                "Registration successful",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
    // Authenticate a registered user using email and password
    public LoginResponse login(LoginRequest request) {

        // Find the user using their email address
        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        // Compare the entered password with the stored BCrypt password
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate JWT token after successful login
        String token = jwtService.generateToken(user.getEmail());

        // Return login details along with the JWT token
        return new LoginResponse(
                "Login successful",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}

