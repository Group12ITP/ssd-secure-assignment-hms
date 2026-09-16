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

    public void sendContactMail(String name, String email, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toAddress);
        mail.setSubject(subject == null || subject.isBlank() ? "New contact message" : subject);
        mail.setText("From: " + name + " <" + email + ">\n\n" + message);
        if (email != null && !email.isBlank()) {
            mail.setReplyTo(email);
        }
        if (fromAddress != null && !fromAddress.isBlank()) {
            mail.setFrom(fromAddress);
        }
        mailSender.send(mail);
    }
}



