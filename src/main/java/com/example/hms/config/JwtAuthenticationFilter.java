package com.example.hms.config;

import com.example.hms.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Project Name : Hospital Management System
 * Module       : JWT Security
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Intercepts incoming requests and validates JWT tokens
 * before allowing access to protected endpoints.
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    // Execute JWT authentication for each incoming request
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Read the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Continue if the request does not contain a Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token after the Bearer prefix
        String token = authHeader.substring(7);

        try {
            // Extract the email stored inside the token
            String email = jwtService.extractUsername(token);

            // Authenticate only if the user is not already authenticated
            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user information from the database
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                // Validate the token against the loaded user
                if (jwtService.isTokenValid(token, userDetails)) {

                    // Create an authenticated security object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // Attach request details to the authentication object
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // Store authentication in Spring Security context
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (JwtException | IllegalArgumentException |
                 UsernameNotFoundException exception) {

            // Clear authentication when the token is invalid or expired
            SecurityContextHolder.clearContext();
        }

        // Continue processing the request
        filterChain.doFilter(request, response);
    }
}
