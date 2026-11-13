
package com.example.hms.config;

import com.example.hms.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Project Name : Hospital Management System
 * Module       : JWT Authentication Filter Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests JWT authentication filter behavior.
 * Verifies Authorization header processing,
 * token validation and SecurityContext authentication.
 */

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {

        // Clear previous authentication before every test
        SecurityContextHolder.clearContext();

        // Prepare registered user details
        userDetails = User.withUsername("hemang@example.com")
                .password("encoded-password")
                .roles("USER")
                .build();
    }

    @Test
    void shouldContinueWhenAuthorizationHeaderIsMissing()
            throws Exception {

        // Mock missing Authorization header
        when(request.getHeader("Authorization")).thenReturn(null);

        // Execute filter
        jwtAuthenticationFilter.doFilterInternal(
                request, response, filterChain
        );

        // Verify filter chain continues
        verify(filterChain).doFilter(request, response);

        // Verify no authentication is created
        assertThat(
                SecurityContextHolder.getContext().getAuthentication()
        ).isNull();
    }

    @Test
    void shouldContinueWhenAuthorizationHeaderIsNotBearer()
            throws Exception {

        // Mock an invalid Authorization prefix
        when(request.getHeader("Authorization"))
                .thenReturn("Basic some-token");

        // Execute filter
        jwtAuthenticationFilter.doFilterInternal(
                request, response, filterChain
        );

        // Verify filter chain continues
        verify(filterChain).doFilter(request, response);

        // Verify JWT service is not called
        verifyNoInteractions(jwtService);
    }

    @Test
    void shouldAuthenticateUserWithValidToken()
            throws Exception {

        // Mock Bearer token
        when(request.getHeader("Authorization"))
                .thenReturn("Bearer valid-token");

        // Mock JWT username extraction
        when(jwtService.extractUsername("valid-token"))
                .thenReturn("hemang@example.com");

        // Mock user lookup
        when(userDetailsService.loadUserByUsername(
                "hemang@example.com"))
                .thenReturn(userDetails);

        // Mock successful token validation
        when(jwtService.isTokenValid("valid-token", userDetails))
                .thenReturn(true);

        // Execute filter
        jwtAuthenticationFilter.doFilterInternal(
                request, response, filterChain
        );

        // Verify authentication is stored
        assertThat(
                SecurityContextHolder.getContext().getAuthentication()
        ).isInstanceOf(UsernamePasswordAuthenticationToken.class);

        assertThat(
                SecurityContextHolder.getContext()
                        .getAuthentication().getName()
        ).isEqualTo("hemang@example.com");

        // Verify filter chain continues
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateUserWithInvalidToken()
            throws Exception {

        // Mock Bearer token
        when(request.getHeader("Authorization"))
                .thenReturn("Bearer invalid-token");

        // Mock token username
        when(jwtService.extractUsername("invalid-token"))
                .thenReturn("hemang@example.com");

        when(userDetailsService.loadUserByUsername(
                "hemang@example.com"))
                .thenReturn(userDetails);

        // Mock failed token validation
        when(jwtService.isTokenValid("invalid-token", userDetails))
                .thenReturn(false);

        // Execute filter
        jwtAuthenticationFilter.doFilterInternal(
                request, response, filterChain
        );

        // Verify no authentication is created
        assertThat(
                SecurityContextHolder.getContext().getAuthentication()
        ).isNull();

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateUserAgainIfAlreadyAuthenticated()
            throws Exception {

        // Set existing authentication
        UsernamePasswordAuthenticationToken existingAuthentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(existingAuthentication);

        // Mock Bearer token
        when(request.getHeader("Authorization"))
                .thenReturn("Bearer valid-token");

        when(jwtService.extractUsername("valid-token"))
                .thenReturn("hemang@example.com");

        // Execute filter
        jwtAuthenticationFilter.doFilterInternal(
                request, response, filterChain
        );

        // Verify existing authentication remains unchanged
        assertThat(
                SecurityContextHolder.getContext().getAuthentication()
        ).isSameAs(existingAuthentication);

        // User should not be loaded again
        verifyNoInteractions(userDetailsService);

        verify(filterChain).doFilter(request, response);
    }
}
