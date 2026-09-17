package com.example.test.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.contact.to}")
    private String toAddress;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private String sanitizeHeader(String header) {
        if (header == null) {
            return "";
        }
        // Strip CRLF characters to prevent SMTP / Email header injection (CWE-93)
        return header.replaceAll("[\\r\\n]", " ").trim();
    }

    public void sendContactMail(String name, String email, String subject, String message) {
        String cleanName = sanitizeHeader(name);
        String cleanEmail = sanitizeHeader(email);
        String cleanSubject = sanitizeHeader(subject);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toAddress);
        mail.setSubject(cleanSubject.isBlank() ? "New contact message" : cleanSubject);
        mail.setText("From: " + cleanName + " <" + cleanEmail + ">\n\n" + (message != null ? message : ""));
        if (!cleanEmail.isBlank()) {
            mail.setReplyTo(cleanEmail);
        }
        if (fromAddress != null && !fromAddress.isBlank()) {
            mail.setFrom(sanitizeHeader(fromAddress));
        }
        mailSender.send(mail);
    }
}



