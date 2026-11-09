
package com.example.hms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication - Registration DTO
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Receives user registration details from the client.
 * Validates name, email and password before processing.
 */

public record RegisterRequest(

        // User's full name
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        // User's email must have a valid email format
        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email,

        // Password must contain at least 8 characters
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must contain at least 8 characters")
        String password

) {
}

