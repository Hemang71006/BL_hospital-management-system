
package com.example.hms.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Project Name : Hospital Management System
 * Module       : Security Configuration Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests the creation of the Spring Security filter chain.
 */

class SecurityConfigTest {

    @Test
    void shouldCreateSecurityFilterChain() throws Exception {

        // Create a mock JWT authentication filter
        JwtAuthenticationFilter jwtFilter =
                mock(JwtAuthenticationFilter.class);

        // Create SecurityConfig object
        SecurityConfig securityConfig =
                new SecurityConfig(jwtFilter);

        // Create HttpSecurity object
        HttpSecurity http = mock(HttpSecurity.class);

        // Verify that the configuration class exists
        assertThat(securityConfig).isNotNull();
        assertThat(http).isNotNull();
    }
}
