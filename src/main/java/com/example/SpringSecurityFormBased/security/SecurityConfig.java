package com.example.SpringSecurityFormBased.security;

import com.example.SpringSecurityFormBased.users.AppUsersDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final AppUsersDetailsService appUsersDetailsService;

    public SecurityConfig(
            PasswordEncoder passwordEncoder,
            AppUsersDetailsService appUsersDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.appUsersDetailsService = appUsersDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                        .requestMatchers(HttpMethod.GET, "/registration/confirm").permitAll()
                        .requestMatchers(HttpMethod.GET, "/registration/find/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider()) // IMPORTANT
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(appUsersDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}