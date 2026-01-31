package com.example.SpringSecurityFormBased.security;

import com.example.SpringSecurityFormBased.users.AppUsersDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AppUsersDetailsService appUsersDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder, AppUsersDetailsService appUsersDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.appUsersDetailsService = appUsersDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize ->{
                    authorize.requestMatchers(HttpMethod.POST, "/registration").permitAll()
                            .requestMatchers(HttpMethod.GET,"/registration/find/**").hasRole("USER")
                            .anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider(appUsersDetailsService, passwordEncoder));
        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService appUsersDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(appUsersDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}