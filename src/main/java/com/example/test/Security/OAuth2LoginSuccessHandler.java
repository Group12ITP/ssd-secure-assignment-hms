package com.example.test.Security;

import com.example.test.Model.Patient;
import com.example.test.Repository.PatientRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public OAuth2LoginSuccessHandler(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            if (email == null || email.isBlank()) {
                logger.error("OAuth2 authentication succeeded but email attribute is missing from provider principal");
                response.sendRedirect("/patient/login?error=email_not_provided");
                return;
            }

            // Find existing patient or auto-provision account
            Patient patient = patientRepository.findByEmail(email);
            if (patient == null) {
                logger.info("Auto-provisioning new patient account for verified OIDC email: {}", email);
                patient = new Patient();
                patient.setEmail(email);
                patient.setFullName((name != null && !name.isBlank()) ? name : "Google User");

                // Ensure unique username
                String baseUsername = email;
                if (patientRepository.findByUsername(baseUsername) != null) {
                    baseUsername = email.split("@")[0] + "_" + UUID.randomUUID().toString().substring(0, 5);
                }
                patient.setUsername(baseUsername);

                patient.setNicPassport("GGL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                patient.setDateOfBirth(LocalDate.of(2000, 1, 1));
                patient.setGender("Other");
                patient.setContactNumber("0000000000");

                // Set unguessable complex hashed password to satisfy entity validation while preventing password login
                patient.setPassword(passwordEncoder.encode(UUID.randomUUID().toString() + "!Aa1@#$"));
                patient = patientRepository.save(patient);
            }

            // Defend against session fixation by regenerating session ID upon successful external authentication
            request.changeSessionId();

            // Establish Spring Security Context with ROLE_PATIENT authority
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(SecurityRoles.ROLE_PATIENT));
            UsernamePasswordAuthenticationToken patientAuth = new UsernamePasswordAuthenticationToken(
                    patient.getUsername(),
                    null,
                    authorities
            );
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(patientAuth);
            SecurityContextHolder.setContext(securityContext);

            // Store in HTTP session for controller authorization and profile access
            HttpSession session = request.getSession();
            session.setAttribute("patient", patient);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

            logger.info("Patient [{}] successfully authenticated via Google OAuth2/OIDC", patient.getUsername());
            response.sendRedirect("/patient/dashboard");
        } else {
            response.sendRedirect("/patient/login?error=invalid_principal");
        }
    }
}
