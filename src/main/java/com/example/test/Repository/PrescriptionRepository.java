package com.example.test.Repository;

import com.example.test.Model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Find prescriptions by patient
    List<Prescription> findByPatientIdOrderByPrescriptionDateDesc(Long patientId);

    // Find prescriptions by doctor
    List<Prescription> findByDoctorIdOrderByPrescriptionDateDesc(Long doctorId);

    // Find prescriptions by appointment
    Prescription findByAppointmentId(Long appointmentId);

    // Find prescriptions by status
    List<Prescription> findByStatus(String status);

    // Find prescriptions by patient and status
    List<Prescription> findByPatientIdAndStatusOrderByPrescriptionDateDesc(Long patientId, String status);

    // Find prescriptions by doctor and status
    List<Prescription> findByDoctorIdAndStatusOrderByPrescriptionDateDesc(Long doctorId, String status);

    // Find active prescriptions for pharmacist (scheduled orders)
    @Query("SELECT p FROM Prescription p WHERE p.status = 'Active' ORDER BY p.prescriptionDate DESC")
    List<Prescription> findActivePrescriptionsForPharmacist();

    // Find prescriptions by date range
    @Query("SELECT p FROM Prescription p WHERE p.prescriptionDate BETWEEN :startDate AND :endDate ORDER BY p.prescriptionDate DESC")
    List<Prescription> findPrescriptionsByDateRange(@Param("startDate") LocalDateTime startDate, 
                                                    @Param("endDate") LocalDateTime endDate);

    // Find prescriptions by patient and date range
    @Query("SELECT p FROM Prescription p WHERE p.patientId = :patientId AND p.prescriptionDate BETWEEN :startDate AND :endDate ORDER BY p.prescriptionDate DESC")
    List<Prescription> findPrescriptionsByPatientAndDateRange(@Param("patientId") Long patientId,
                                                              @Param("startDate") LocalDateTime startDate, 
                                                              @Param("endDate") LocalDateTime endDate);

    // Count prescriptions by status
    Long countByStatus(String status);

    // Count prescriptions by doctor and status
    Long countByDoctorIdAndStatus(Long doctorId, String status);

    // Count prescriptions by patient and status
    Long countByPatientIdAndStatus(Long patientId, String status);

    // Find urgent prescriptions
    List<Prescription> findByIsUrgentTrueAndStatusOrderByPrescriptionDateDesc(String status);

    // Find prescriptions with follow-up dates
    @Query("SELECT p FROM Prescription p WHERE p.followUpDate IS NOT NULL AND p.followUpDate >= :currentDate ORDER BY p.followUpDate ASC")
    List<Prescription> findPrescriptionsWithUpcomingFollowUp(@Param("currentDate") LocalDateTime currentDate);

    // Count prescriptions completed today
    @Query("SELECT COUNT(p) FROM Prescription p WHERE p.status = 'Completed' AND p.prescriptionDate BETWEEN :start AND :end")
    Long countCompletedToday(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}


