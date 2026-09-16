package com.example.test.Service;

import com.example.test.Model.Doctor;
import com.example.test.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register doctor
    public Doctor registerDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor.getEmail()) != null)
            throw new RuntimeException("Email already exists!");

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    // Login doctor
    public Doctor loginDoctor(String username, String rawPassword) {
        Doctor doctor = doctorRepository.findByUsername(username);
        if (doctor == null)
            throw new RuntimeException("Invalid username or password.");

        if (!passwordEncoder.matches(rawPassword, doctor.getPassword()))
            throw new RuntimeException("Invalid username or password.");

        return doctor;
    }

    public Doctor getDoctorByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }

}
