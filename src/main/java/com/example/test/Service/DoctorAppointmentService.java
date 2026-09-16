package com.example.test.Service;

import com.example.test.Model.DoctorAppointment;
import com.example.test.Repository.DoctorAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DoctorAppointmentService {

    @Autowired
    private DoctorAppointmentRepository appointmentRepository;

    public List<DoctorAppointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<DoctorAppointment> getTodayAppointments(Long doctorId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    public long countAllAppointments(Long doctorId) {
        return appointmentRepository.countByDoctorId(doctorId);
    }

    public long countTodayAppointments(Long doctorId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return appointmentRepository.countByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    public long countByStatus(Long doctorId, String status) {
        return appointmentRepository.countByDoctorIdAndStatus(doctorId, status);
    }

    public DoctorAppointment updateAppointmentStatus(Long appointmentId, String status) {
        DoctorAppointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    public DoctorAppointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    public DoctorAppointment saveAppointment(DoctorAppointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<DoctorAppointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    public List<DoctorAppointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
}
