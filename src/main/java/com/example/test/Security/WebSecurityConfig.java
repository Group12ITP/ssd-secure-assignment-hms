package com.example.test.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .authorizeHttpRequests(auth -> auth
                        // 1. Static assets
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()

                        // 2. Public pages, error, and landing
                        .requestMatchers(
                                "/",
                                "/home",
                                "/logins",
                                "/registers",
                                "/contact/**",
                                "/error"
                        ).permitAll()

                        // 3. Public authentication endpoints (Login & Register & Logout)
                        .requestMatchers(
                                "/doctor/login",
                                "/doctor/register",
                                "/pharmacist/login",
                                "/pharmacist/register",
                                "/patient/login",
                                "/patient/register",
                                "/patient/logout"
                        ).permitAll()

                        // 4. Role-specific portals
                        .requestMatchers("/doctor/**").hasRole(SecurityRoles.DOCTOR)
                        .requestMatchers("/pharmacist/**").hasRole(SecurityRoles.PHARMACIST)
                        .requestMatchers("/medicine/**").hasRole(SecurityRoles.PHARMACIST)
                        .requestMatchers("/patient/**").hasRole(SecurityRoles.PATIENT)

                        // 5. Fallback: all other requests require authentication
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> res.sendRedirect("/logins"))
                        .accessDeniedHandler((req, res, e) -> res.sendRedirect("/logins?denied=true"))
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logins?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }
}
