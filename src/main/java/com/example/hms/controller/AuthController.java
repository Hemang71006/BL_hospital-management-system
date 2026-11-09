
package com.example.hms.controller;

import com.example.hms.dto.RegisterRequest;
import com.example.hms.dto.RegisterResponse;
import com.example.hms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication - Controller
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Exposes REST endpoints for user authentication.
 * Handles incoming registration requests.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Inject authentication service
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

