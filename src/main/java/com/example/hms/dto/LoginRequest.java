
package com.example.hms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication - Login DTO
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Receives login credentials from the client.
 * Validates email and password fields.
 */

public record LoginRequest(

        // Registered user's email address
        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email,

        // User's login password
        @NotBlank(message = "Password is required")
        String password

) {
}

