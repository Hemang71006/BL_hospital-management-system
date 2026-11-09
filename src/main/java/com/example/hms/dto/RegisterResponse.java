
package com.example.hms.dto;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication - Registration Response
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Returns user registration details to the client.
 * Does not expose the user's password.
 */

public record RegisterResponse(

        // Registration result message
        String message,

        // Newly generated user ID
        Long userId,

        // Registered user's name
        String name,

        // Registered user's email
        String email,

        // Assigned user role
        String role

) {
}

