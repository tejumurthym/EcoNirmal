package com.econirmal.reporting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow all static resources and HTML pages
                .requestMatchers("/**").permitAll()
                // Allow authentication APIs
                .requestMatchers("/api/auth/**").permitAll()
                // Protect other APIs with roles
                .requestMatchers("/api/reports/submit").hasRole("CITIZEN")
                .requestMatchers("/api/worker/**").hasRole("WORKER")
                .requestMatchers("/api/admin/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}