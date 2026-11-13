
package com.example.hms.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Project Name : Hospital Management System
 * Module       : Password Configuration Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests BCrypt password encoder configuration.
 * Verifies password encoding and password matching.
 */

import static org.assertj.core.api.Assertions.assertThat;

class PasswordConfigTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

        // Create PasswordConfig object
        PasswordConfig passwordConfig = new PasswordConfig();

        // Obtain PasswordEncoder bean
        passwordEncoder = passwordConfig.passwordEncoder();
    }

    @Test
    void shouldCreateBCryptPasswordEncoder() {

        // Verify encoder implementation
        assertThat(passwordEncoder)
                .isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    void shouldEncodePassword() {

        String rawPassword = "password123";

        // Encode original password
        String encodedPassword =
                passwordEncoder.encode(rawPassword);

        assertThat(encodedPassword).isNotNull();
        assertThat(encodedPassword).isNotEqualTo(rawPassword);
    }

    @Test
    void shouldMatchCorrectPassword() {

        String rawPassword = "password123";

        // Generate encoded password
        String encodedPassword =
                passwordEncoder.encode(rawPassword);

        // Verify original password matches hash
        assertThat(
                passwordEncoder.matches(rawPassword, encodedPassword)
        ).isTrue();
    }

    @Test
    void shouldNotMatchIncorrectPassword() {

        String rawPassword = "password123";

        // Encode original password
        String encodedPassword =
                passwordEncoder.encode(rawPassword);

        // Verify incorrect password does not match
        assertThat(
                passwordEncoder.matches("wrongpassword", encodedPassword)
        ).isFalse();
    }
}
