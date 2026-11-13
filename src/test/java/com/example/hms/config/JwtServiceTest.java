
package com.example.hms.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Project Name : Hospital Management System
 * Module       : JWT Service Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests JWT token generation, username extraction,
 * token validation and expiration handling.
 */

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET =
            Base64.getEncoder().encodeToString(
                    "01234567890123456789012345678901"
                            .getBytes(StandardCharsets.UTF_8)
            );

    @BeforeEach
    void setUp() {

        // Create JWT service object
        jwtService = new JwtService();

        // Inject JWT configuration values
        ReflectionTestUtils.setField(jwtService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtService, "expiration", 60000L);
    }

    @Test
    void shouldGenerateJwtToken() {

        // Generate token for registered user
        String token = jwtService.generateToken("hemang@example.com");

        // Verify token is generated
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
    }

    @Test
    void shouldExtractUsernameFromToken() {

        // Generate JWT token
        String token = jwtService.generateToken("hemang@example.com");

        // Extract email from token
        String username = jwtService.extractUsername(token);

        // Verify extracted email
        assertThat(username).isEqualTo("hemang@example.com");
    }

    @Test
    void shouldAcceptValidTokenForCorrectUser() {

        // Generate token
        String token = jwtService.generateToken("hemang@example.com");

        // Prepare matching user details
        UserDetails userDetails = User.withUsername("hemang@example.com")
                .password("encoded-password")
                .roles("USER")
                .build();

        // Validate token
        boolean result = jwtService.isTokenValid(token, userDetails);

        assertThat(result).isTrue();
    }

    @Test
    void shouldRejectTokenForDifferentUser() {

        // Generate token for one user
        String token = jwtService.generateToken("hemang@example.com");

        // Prepare different user details
        UserDetails userDetails = User.withUsername("other@example.com")
                .password("encoded-password")
                .roles("USER")
                .build();

        // Validate token against different user
        boolean result = jwtService.isTokenValid(token, userDetails);

        assertThat(result).isFalse();
    }

    @Test
    void shouldRejectExpiredToken() {

        // Configure an already expired token
        ReflectionTestUtils.setField(jwtService, "expiration", -1000L);

        // Generate expired token
        String token = jwtService.generateToken("hemang@example.com");

        // Prepare matching user details
        UserDetails userDetails = User.withUsername("hemang@example.com")
                .password("encoded-password")
                .roles("USER")
                .build();

        // Expired tokens are rejected during JWT parsing
        assertThrows(
                ExpiredJwtException.class,
                () -> jwtService.isTokenValid(token, userDetails)
        );
    }
}
