
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

    // Test saving a user and finding the user by email
    @Test
    void shouldSaveAndFindUserByEmail() {

        User user = User.builder()
                .name("Test User")
                .email("repository.test@example.com")
                .password("encodedPassword123")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        Optional<User> result =
                userRepository.findByEmail("repository.test@example.com");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test User");
        assertThat(result.get().getEmail())
                .isEqualTo("repository.test@example.com");
        assertThat(result.get().getRole()).isEqualTo(Role.USER);
    }

    // Test checking whether an email already exists
    @Test
    void shouldReturnTrueWhenEmailExists() {

        User user = User.builder()
                .name("Existing User")
                .email("existing.user@example.com")
                .password("encodedPassword123")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        boolean result =
                userRepository.existsByEmail("existing.user@example.com");

        assertThat(result).isTrue();
    }

    // Test checking an email that does not exist
    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {

        boolean result =
                userRepository.existsByEmail("unknown.user@example.com");

        assertThat(result).isFalse();
    }

    // Test finding a user with an email that does not exist
    @Test
    void shouldReturnEmptyWhenUserEmailDoesNotExist() {

        Optional<User> result =
                userRepository.findByEmail("missing.user@example.com");

        assertThat(result).isEmpty();
    }
}
