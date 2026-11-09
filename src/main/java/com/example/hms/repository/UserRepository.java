
package com.example.hms.repository;

import com.example.hms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project Name : Hospital Management System
 * Module       : Repository
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Provides database operations for registered users.
 * Supports searching users using their email addresses.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    // Find a registered user using their email address
    Optional<User> findByEmail(String email);

    // Check whether an email address is already registered
    boolean existsByEmail(String email);
}

