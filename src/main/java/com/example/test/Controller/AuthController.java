package com.example.test.Controller;


import com.example.test.Model.Pharmacist;
import com.example.test.Model.Prescription;
import com.example.test.Service.PharmacistService;
import com.example.test.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
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
            model.addAttribute("pharmacist", pharmacist);
            // remember username for dashboard fetches
            request.getSession().setAttribute("pharmacistUsername", pharmacist.getUsername());
            
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
