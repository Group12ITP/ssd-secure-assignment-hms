package com.example.test.Service;

import com.example.test.Model.Pharmacist;
import com.example.test.Repository.PharmacistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PharmacistService {

    @Autowired
    private PharmacistRepository pharmacistRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new pharmacist
    public Pharmacist registerPharmacist(Pharmacist pharmacist) {
        if (pharmacistRepository.findByEmail(pharmacist.getEmail()) != null)
            throw new RuntimeException("Email already exists!");

        pharmacist.setPassword(passwordEncoder.encode(pharmacist.getPassword()));
        return pharmacistRepository.save(pharmacist);
    }

    // Login pharmacist
    public Pharmacist loginPharmacist(String username, String rawPassword) {
        Pharmacist pharmacist = pharmacistRepository.findByUsername(username);
        if (pharmacist == null)
            throw new RuntimeException("Invalid username or password.");

        if (!passwordEncoder.matches(rawPassword, pharmacist.getPassword()))
            throw new RuntimeException("Invalid username or password.");

        return pharmacist;
    }

    // Get pharmacist by username
    public Pharmacist getPharmacistByUsername(String username) {
        return pharmacistRepository.findByUsername(username);
    }
}
