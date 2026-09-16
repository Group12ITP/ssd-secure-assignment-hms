package com.example.test.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF for simplicity (enable later for production)
                .authorizeHttpRequests(auth -> auth
                        // Public pages (accessible without login)
                        .requestMatchers(
                                "/pharmacist/register",
                                "/pharmacist/login",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        // Everything else requires authentication
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/pharmacist/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}
