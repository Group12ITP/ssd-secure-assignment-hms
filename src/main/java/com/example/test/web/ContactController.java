package com.example.test.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/contact")
    public String contactGet(RedirectAttributes redirectAttributes) {
        // Avoid 405/404 if someone GETs /contact
        return "redirect:/home";
    }

    @PostMapping(path = {"/contact", "/contact/"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String handleContact(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam(required = false) String subject,
                                @RequestParam("message") String message,
                                RedirectAttributes redirectAttributes) {
        try {
            emailService.sendContactMail(name, email, subject, message);
            redirectAttributes.addAttribute("success", 1);
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", 1);
        }
        return "redirect:/home";
    }
}



