package com.example.test.Repository;


import com.example.test.Model.DoctorAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, Long> {
    List<DoctorAppointment> findByDoctorId(Long doctorId);

    // Count helpers
    long countByDoctorId(Long doctorId);
    long countByDoctorIdAndStatus(Long doctorId, String status);

    // Date range filters
    List<DoctorAppointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
    long countByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    // Additional methods for appointment management
    List<DoctorAppointment> findByStatus(String status);
    List<DoctorAppointment> findByPatientId(Long patientId);
    List<DoctorAppointment> findByDoctorIdAndStatus(Long doctorId, String status);
    List<DoctorAppointment> findByPatientIdAndStatus(Long patientId, String status);
}

