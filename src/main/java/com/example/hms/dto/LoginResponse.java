package com.example.hms.dto;

import com.example.hms.entity.Role;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication DTO
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Returns login information along with the generated JWT token.
 */

public record LoginResponse(
        String message,
        Long userId,
        String name,
        String email,
        Role role,
        String token
) {
}