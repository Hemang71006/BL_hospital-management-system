
package com.example.hms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Project Name : Hospital Management System
 * Module       : Security Configuration
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Configures BCrypt password encryption.
 * Provides PasswordEncoder as a Spring-managed bean.
 */

@Configuration
public class PasswordConfig {

    // Create a BCrypt encoder for securely storing passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

