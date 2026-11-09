
package com.example.hms.dto;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication - Login Response
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Returns login information to the client.
 * JWT token will be added during JWT integration.
 */

public record LoginResponse(

        // Login success message
        String message,

        // Logged-in user's ID
        Long userId,

        // Logged-in user's name
        String name,

        // Logged-in user's email
        String email,

        // Logged-in user's role
        String role

) {
}

