package com.example.hms.dto;

import com.example.hms.entity.Role;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication DTO
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Returns the registration result after creating a user account.
 */

public record RegisterResponse(
        String message,
        Long userId,
        String name,
        String email,
        Role role
) {
}