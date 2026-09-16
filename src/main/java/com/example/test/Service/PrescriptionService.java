package com.example.test.Service;

import com.example.test.Model.Prescription;
import com.example.test.Repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        try {
            System.out.println("=== PRESCRIPTION SERVICE DEBUG ===");
            System.out.println("Creating prescription with data: " + prescription);
            
            prescription.setPrescriptionDate(LocalDateTime.now());
            prescription.setStatus("Active");
            
            System.out.println("Prescription before save: " + prescription);
            Prescription saved = prescriptionRepository.save(prescription);
            System.out.println("Prescription after save: " + saved);
            System.out.println("Saved prescription ID: " + saved.getPrescriptionId());
            
            return saved;
        } catch (Exception e) {
            System.err.println("Error in PrescriptionService.createPrescription: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientIdOrderByPrescriptionDateDesc(patientId);
    }

    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorIdOrderByPrescriptionDateDesc(doctorId);
    }

    public List<Prescription> getActivePrescriptionsForPharmacist() {
        return prescriptionRepository.findActivePrescriptionsForPharmacist();
    }

    public Prescription getPrescriptionByAppointment(Long appointmentId) {
        return prescriptionRepository.findByAppointmentId(appointmentId);
    }

    public List<Prescription> getPrescriptionsByStatus(String status) {
        return prescriptionRepository.findByStatus(status);
    }

    public List<Prescription> getPrescriptionsByPatientAndStatus(Long patientId, String status) {
        return prescriptionRepository.findByPatientIdAndStatusOrderByPrescriptionDateDesc(patientId, status);
    }

    public List<Prescription> getPrescriptionsByDoctorAndStatus(Long doctorId, String status) {
        return prescriptionRepository.findByDoctorIdAndStatusOrderByPrescriptionDateDesc(doctorId, status);
    }

    public Prescription updatePrescriptionStatus(Long prescriptionId, String status) {
        Optional<Prescription> prescriptionOpt = prescriptionRepository.findById(prescriptionId);
        if (prescriptionOpt.isPresent()) {
            Prescription prescription = prescriptionOpt.get();
            prescription.setStatus(status);
            return prescriptionRepository.save(prescription);
        }
        return null;
    }

    public Prescription updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Optional<Prescription> getPrescriptionById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }

    public List<Prescription> getUrgentPrescriptions() {
        return prescriptionRepository.findByIsUrgentTrueAndStatusOrderByPrescriptionDateDesc("Active");
    }

    public List<Prescription> getPrescriptionsWithUpcomingFollowUp() {
        return prescriptionRepository.findPrescriptionsWithUpcomingFollowUp(LocalDateTime.now());
    }

    public Long countPrescriptionsByStatus(String status) {
        return prescriptionRepository.countByStatus(status);
    }

    public Long countCompletedToday() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusDays(1);
        return prescriptionRepository.countCompletedToday(start, end);
    }

    public Long countPrescriptionsByDoctorAndStatus(Long doctorId, String status) {
        return prescriptionRepository.countByDoctorIdAndStatus(doctorId, status);
    }

    public Long countPrescriptionsByPatientAndStatus(Long patientId, String status) {
        return prescriptionRepository.countByPatientIdAndStatus(patientId, status);
    }

    public List<Prescription> getPrescriptionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return prescriptionRepository.findPrescriptionsByDateRange(start, end);
    }
}
