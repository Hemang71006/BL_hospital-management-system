
package com.example.hms.service;

import com.example.hms.dto.RegisterRequest;
import com.example.hms.dto.RegisterResponse;
import com.example.hms.entity.Role;
import com.example.hms.entity.User;
import com.example.hms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    // Inject repository and password encoder
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        // Return registration details without exposing the password
        return new RegisterResponse(
                "User registered successfully",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().name()
        );
    }
}

