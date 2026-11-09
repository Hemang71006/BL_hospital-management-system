package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project Name : Hospital Management System
 * Module       : Entity
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Represents a registered user account in the system.
 * Stores user details, encrypted password and assigned role.
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // Generate a unique primary key for every user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store the user's full name
    @Column(nullable = false, length = 100)
    private String name;

    // Store a unique email address for authentication
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // Store the BCrypt-encoded password
    @Column(nullable = false)
    private String password;

    // Store the user's assigned role as a string in the database
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
}
