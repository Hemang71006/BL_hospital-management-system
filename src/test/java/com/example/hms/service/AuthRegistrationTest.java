
package com.example.hms.service;

import com.example.hms.dto.RegisterRequest;
import com.example.hms.dto.RegisterResponse;
import com.example.hms.entity.Role;
import com.example.hms.entity.User;
import com.example.hms.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Project Name : Hospital Management System
 * Module       : Authentication Registration Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests user registration functionality in AuthService.
 * Verifies password encoding, default role assignment,
 * email normalization and duplicate email prevention.
 */

@ExtendWith(MockitoExtension.class)
class AuthRegistrationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private User savedUser;

    @BeforeEach
    void setUp() {

        // Prepare registration request
        registerRequest = new RegisterRequest(
                "Hemang",
                "hemang@example.com",
                "password123"
        );

        // Prepare the user returned by the repository
        savedUser = User.builder()
                .id(1L)
                .name("Hemang")
                .email("hemang@example.com")
                .password("encoded-password")
                .role(Role.USER)
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {

        // Mock password encoding
        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        // Mock database save operation
        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // Call registration method
        RegisterResponse response =
                authService.register(registerRequest);

        // Verify response
        assertThat(response.message())
                .isEqualTo("User registered successfully");

        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Hemang");
        assertThat(response.email()).isEqualTo("hemang@example.com");
        assertThat(response.role()).isEqualTo("USER");

        // Verify repository save was called
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldEncodePasswordBeforeSavingUser() {

        // Mock password encoding
        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // Capture the user passed to repository
        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        // Register user
        authService.register(registerRequest);

        verify(userRepository).save(userCaptor.capture());

        // Verify encoded password was saved
        User capturedUser = userCaptor.getValue();

        assertThat(capturedUser.getPassword())
                .isEqualTo("encoded-password");

        verify(passwordEncoder).encode("password123");
    }

    @Test
    void shouldAssignDefaultUserRole() {

        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // Capture saved user
        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        authService.register(registerRequest);

        verify(userRepository).save(userCaptor.capture());

        // Verify default role
        assertThat(userCaptor.getValue().getRole())
                .isEqualTo(Role.USER);
    }

    @Test
    void shouldNormalizeEmailBeforeSaving() {

        RegisterRequest request = new RegisterRequest(
                " Hemang ",
                "  HEMANG@EXAMPLE.COM  ",
                "password123"
        );

        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        // Register user with spaces and uppercase email
        authService.register(request);

        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        // Verify name and email normalization
        assertThat(capturedUser.getName()).isEqualTo("Hemang");
        assertThat(capturedUser.getEmail())
                .isEqualTo("hemang@example.com");
    }

    @Test
    void shouldRejectDuplicateEmail() {

        // Mock existing email
        when(userRepository.existsByEmail("hemang@example.com"))
                .thenReturn(true);

        // Verify registration is rejected
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(registerRequest)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Email is already registered");

        // Verify user is not saved and password is not encoded
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void shouldReturnCorrectRegistrationResponse() {

        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        RegisterResponse response =
                authService.register(registerRequest);

        // Verify every response field
        assertThat(response.message())
                .isEqualTo("User registered successfully");

        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Hemang");
        assertThat(response.email()).isEqualTo("hemang@example.com");
        assertThat(response.role()).isEqualTo("USER");
    }
}
