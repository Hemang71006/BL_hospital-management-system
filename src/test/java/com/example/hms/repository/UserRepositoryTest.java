
package com.example.hms.repository;

import com.example.hms.entity.Role;
import com.example.hms.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Project Name : Hospital Management System
 * Module       : User Repository Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests database operations provided by UserRepository.
 * Verifies saving users, finding users by email and
 * checking whether an email already exists.
 */

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // Test whether a user can be saved and retrieved using email
    @Test
    void shouldSaveAndFindUserByEmail() {

        // Create a sample user
        User user = User.builder()
                .name("Test User")
                .email("repository.test@example.com")
                .password("encodedPassword123")
                .role(Role.USER)
                .build();

        // Save the user in the database
        userRepository.save(user);

        // Retrieve the user using email
        Optional<User> result =
                userRepository.findByEmail("repository.test@example.com");

        // Verify the retrieved user
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test User");
        assertThat(result.get().getEmail())
                .isEqualTo("repository.test@example.com");
        assertThat(result.get().getRole()).isEqualTo(Role.USER);
    }

    // Test whether the repository detects an existing email
    @Test
    void shouldReturnTrueWhenEmailExists() {

        // Create a sample user
        User user = User.builder()
                .name("Existing User")
                .email("existing.user@example.com")
                .password("encodedPassword123")
                .role(Role.USER)
                .build();

        // Save the user
        userRepository.save(user);

        // Check whether the email exists
        boolean result =
                userRepository.existsByEmail("existing.user@example.com");

        // Verify the result
        assertThat(result).isTrue();
    }

    // Test whether the repository returns false for an unknown email
    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {

        // Check an email that has not been registered
        boolean result =
                userRepository.existsByEmail("unknown.user@example.com");

        // Verify the result
        assertThat(result).isFalse();
    }

    // Test whether searching for an unknown email returns an empty Optional
    @Test
    void shouldReturnEmptyWhenUserEmailDoesNotExist() {

        // Search for a user using an unknown email
        Optional<User> result =
                userRepository.findByEmail("missing.user@example.com");

        // Verify that no user was found
        assertThat(result).isEmpty();
    }
}
