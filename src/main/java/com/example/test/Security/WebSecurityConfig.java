package com.example.test.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(frame -> frame.deny())
                        .contentTypeOptions(contentType -> {})
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://cdnjs.cloudflare.com; style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://cdnjs.cloudflare.com; font-src 'self' https://cdnjs.cloudflare.com data:; img-src 'self' data: https:; connect-src 'self' https://api.emailjs.com;")
                        )
                        .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                        .permissionsPolicy(permissions -> permissions.policy("camera=(), microphone=(), geolocation=()"))
                )
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
