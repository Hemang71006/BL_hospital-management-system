
package com.example.hms.service;

import com.example.hms.entity.Role;
import com.example.hms.entity.User;
import com.example.hms.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Project Name : Hospital Management System
 * Module       : Custom User Details Service Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests loading registered users for Spring Security.
 * Verifies username, password, role and missing-user handling.
 */

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User existingUser;

    @BeforeEach
    void setUp() {

        // Prepare an existing database user
        existingUser = User.builder()
                .id(1L)
                .name("Hemang")
                .email("hemang@example.com")
                .password("encoded-password")
                .role(Role.USER)
                .build();
    }

    @Test
    void shouldLoadExistingUserSuccessfully() {

        // Mock repository lookup
        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        // Load user details
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        "hemang@example.com"
                );

        // Verify user details are returned
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername())
                .isEqualTo("hemang@example.com");
    }

    @Test
    void shouldReturnCorrectUsername() {

        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        "hemang@example.com"
                );

        // Verify email is used as username
        assertThat(userDetails.getUsername())
                .isEqualTo(existingUser.getEmail());
    }

    @Test
    void shouldReturnCorrectPasswordAndRole() {

        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        "hemang@example.com"
                );

        // Verify encoded password
        assertThat(userDetails.getPassword())
                .isEqualTo("encoded-password");

        // Verify assigned role
        assertThat(userDetails.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_USER");
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        // Mock missing user
        when(userRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        // Verify exception
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(
                        "unknown@example.com"
                )
        );

        assertThat(exception.getMessage())
                .isEqualTo("User not found");
    }
}
