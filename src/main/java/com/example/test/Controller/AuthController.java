package com.example.test.Controller;


import com.example.test.Model.Pharmacist;
import com.example.test.Model.Prescription;
import com.example.test.Service.PharmacistService;
import com.example.test.Service.PrescriptionService;
import com.example.test.Security.SecurityRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pharmacist")
public class AuthController {

    @Autowired
    private PharmacistService pharmacistService;

    @Autowired
    private PrescriptionService prescriptionService;

    // Registration form page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("pharmacist", new Pharmacist());
        return "pharmacist/pharmacist-register";
    }

    // Handle registration submission
    @PostMapping("/register")
    public String registerPharmacist(@ModelAttribute Pharmacist pharmacist, Model model) {
        try {
            pharmacistService.registerPharmacist(pharmacist);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "redirect:/pharmacist/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pharmacist/pharmacist-register";
        }
    }

    // Login form page
    @GetMapping("/login")
    public String showLoginForm() {
        return "pharmacist/pharmacist-login";
    }

    // Handle login submission
    @PostMapping("/login")
    public String loginPharmacist(@RequestParam String username,
                                  @RequestParam String password,
                                  HttpServletRequest request,
                                  Model model) {
        try {
            Pharmacist pharmacist = pharmacistService.loginPharmacist(username, password);
            // Regenerate session ID upon authentication to prevent session fixation attacks
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            model.addAttribute("pharmacist", pharmacist);
            // remember username for dashboard fetches
            request.getSession().setAttribute("pharmacistUsername", pharmacist.getUsername());

            // Establish Spring Security authentication context with PHARMACIST role
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(SecurityRoles.ROLE_PHARMACIST));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(pharmacist.getUsername(), null, authorities);
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(auth);
            SecurityContextHolder.setContext(securityContext);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            
            // Get scheduled orders for pharmacist
            List<Prescription> orders = prescriptionService.getActivePrescriptionsForPharmacist();
            model.addAttribute("orders", orders);
            // Redirect avoids double form submit and keeps URL stable
            return "redirect:/pharmacist/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pharmacist/pharmacist-login";
        }
    }
}
