package com.monopoco.history.config;

import com.monopoco.history.filter.CustomerAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Project: Server
 * Package: com.monopoco.authservice.config
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 18:10
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomerAuthorizationFilter customerAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(customerAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
