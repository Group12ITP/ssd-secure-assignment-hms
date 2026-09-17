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

            // 1. Verify that email_verified claim is true before trusting email identity
            Object emailVerifiedObj = oAuth2User.getAttribute("email_verified");
            boolean emailVerified = Boolean.TRUE.equals(emailVerifiedObj)
                    || "true".equalsIgnoreCase(String.valueOf(emailVerifiedObj));
            if (!emailVerified) {
                logger.warn("OAuth2 authentication rejected: email [{}] is not marked as verified by provider.", email);
                response.sendRedirect("/patient/login?error=unverified_email");
                return;
            }

            // 2. Extract Google 'sub' claim as a stable, persistent identifier
            String sub = oAuth2User.getAttribute("sub");
            if (sub == null || sub.isBlank()) {
                sub = oAuth2User.getName();
            }

            // 3. First look up by Google sub; fallback to email lookup (linking sub to existing patient)
            Patient patient = null;
            if (sub != null && !sub.isBlank()) {
                patient = patientRepository.findByGoogleSub(sub);
            }

            if (patient == null) {
                patient = patientRepository.findByEmail(email);
                if (patient != null && sub != null && !sub.isBlank()) {
                    // Link existing patient account to Google sub identifier
                    patient.setGoogleSub(sub);
                    patient = patientRepository.save(patient);
                    logger.info("Linked existing patient [{}] to Google sub [{}]", patient.getUsername(), sub);
                }
            }

            // If no existing patient matches, JIT provision a new patient account
            if (patient == null) {
                logger.info("Auto-provisioning new patient account for verified OIDC email: {} (sub: {})", email, sub);
                patient = new Patient();
                patient.setGoogleSub(sub);
                patient.setEmail(email);
                patient.setFullName((name != null && !name.isBlank()) ? name : "Google User");

                // Ensure unique username without trusting any request parameters
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

            // 4. Defend against session fixation by regenerating session ID (guarded against no-session state)
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }

            // 5. Establish Spring Security Context strictly with ROLE_PATIENT authority (no request params trusted)
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
