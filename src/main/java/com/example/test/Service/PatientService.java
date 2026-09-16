package com.example.test.Service;

import com.example.test.Model.Patient;
import com.example.test.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register patient
    public Patient registerPatient(Patient patient) {
        // Check if email already exists
        if (patientRepository.findByEmail(patient.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }

        // Check if username already exists
        if (patientRepository.findByUsername(patient.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        // Check if NIC/Passport already exists
        if (patientRepository.findByNicPassport(patient.getNicPassport()) != null) {
            throw new RuntimeException("NIC/Passport number already exists!");
        }

        // Encrypt password
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        return patientRepository.save(patient);
    }

    // Login patient
    public Patient loginPatient(String username, String rawPassword) {
        Patient patient = patientRepository.findByUsername(username);
        if (patient == null) {
            throw new RuntimeException("Invalid username or password.");
        }

        if (!passwordEncoder.matches(rawPassword, patient.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }

        return patient;
    }

    // Update patient profile
    public Patient updatePatient(Long patientId, Patient patientDetails) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        // Update fields
        if (patientDetails.getFullName() != null) patient.setFullName(patientDetails.getFullName());
        if (patientDetails.getContactNumber() != null) patient.setContactNumber(patientDetails.getContactNumber());
        if (patientDetails.getAddress() != null) patient.setAddress(patientDetails.getAddress());
        if (patientDetails.getAllergies() != null) patient.setAllergies(patientDetails.getAllergies());
        if (patientDetails.getChronicConditions() != null) patient.setChronicConditions(patientDetails.getChronicConditions());
        if (patientDetails.getCurrentMedications() != null) patient.setCurrentMedications(patientDetails.getCurrentMedications());
        if (patientDetails.getEmergencyContactName() != null) patient.setEmergencyContactName(patientDetails.getEmergencyContactName());
        if (patientDetails.getEmergencyContactNumber() != null) patient.setEmergencyContactNumber(patientDetails.getEmergencyContactNumber());

        return patientRepository.save(patient);
    }

    // Find patient by ID
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
    }
}