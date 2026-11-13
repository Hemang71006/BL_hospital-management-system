
package com.example.hms.service;

import com.example.hms.dto.LoginRequest;
import com.example.hms.dto.LoginResponse;
import com.example.hms.entity.Role;
import com.example.hms.entity.User;
import com.example.hms.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication Login Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests user login functionality in AuthService.
 * Verifies user lookup, password matching, email
 * normalization and login response generation.
 */

@ExtendWith(MockitoExtension.class)
class AuthLoginTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private User existingUser;

    @BeforeEach
    void setUp() {

        // Prepare login request
        loginRequest = new LoginRequest(
                "hemang@example.com",
                "password123"
        );

        // Prepare an existing registered user
        existingUser = User.builder()
                .id(1L)
                .name("Hemang")
                .email("hemang@example.com")
                .password("encoded-password")
                .role(Role.USER)
                .build();
    }

    @Test
    void shouldLoginUserSuccessfully() {

        // Mock user lookup
        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        // Mock successful password comparison
        when(passwordEncoder.matches(
                "password123", "encoded-password"))
                .thenReturn(true);

        // Call login method
        LoginResponse response = authService.login(loginRequest);

        // Verify successful login
        assertThat(response.message()).isEqualTo("Login successful");
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Hemang");
        assertThat(response.email()).isEqualTo("hemang@example.com");
        assertThat(response.role()).isEqualTo("USER");
    }

    @Test
    void shouldVerifyPasswordUsingPasswordEncoder() {

        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        when(passwordEncoder.matches(
                "password123", "encoded-password"))
                .thenReturn(true);

        // Perform login
        authService.login(loginRequest);

        // Verify password comparison was performed
        verify(passwordEncoder).matches(
                "password123", "encoded-password");
    }

    @Test
    void shouldRejectLoginWithIncorrectPassword() {

        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        // Mock incorrect password
        when(passwordEncoder.matches(
                "wrongpassword", "encoded-password"))
                .thenReturn(false);

        LoginRequest incorrectRequest = new LoginRequest(
                "hemang@example.com",
                "wrongpassword"
        );

        // Verify login is rejected
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(incorrectRequest)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Invalid email or password");
    }

    @Test
    void shouldRejectLoginWhenEmailDoesNotExist() {

        // Mock user not found
        when(userRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        LoginRequest unknownRequest = new LoginRequest(
                "unknown@example.com",
                "password123"
        );

        // Verify login is rejected
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(unknownRequest)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Invalid email or password");

        // Password comparison should not happen
        verify(passwordEncoder, never())
                .matches(anyString(), anyString());
    }

    @Test
    void shouldNormalizeEmailBeforeSearchingUser() {

        LoginRequest request = new LoginRequest(
                "  HEMANG@EXAMPLE.COM  ",
                "password123"
        );

        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        when(passwordEncoder.matches(
                "password123", "encoded-password"))
                .thenReturn(true);

        // Perform login with uppercase email and spaces
        authService.login(request);

        // Verify normalized email was used
        verify(userRepository).findByEmail("hemang@example.com");
    }

    @Test
    void shouldReturnCorrectLoginResponse() {

        when(userRepository.findByEmail("hemang@example.com"))
                .thenReturn(Optional.of(existingUser));

        when(passwordEncoder.matches(
                "password123", "encoded-password"))
                .thenReturn(true);

        LoginResponse response = authService.login(loginRequest);

        // Verify all response fields
        assertThat(response.message())
                .isEqualTo("Login successful");

        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Hemang");
        assertThat(response.email()).isEqualTo("hemang@example.com");
        assertThat(response.role()).isEqualTo("USER");
    }
}
