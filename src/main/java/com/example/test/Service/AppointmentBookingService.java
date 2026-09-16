package com.example.test.Service;

import com.example.test.Model.DoctorAppointment;
import com.example.test.Repository.DoctorAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentBookingService {

    @Autowired
    private DoctorAppointmentRepository appointmentRepository;

    // Available time slots
    private static final String[] TIME_SLOTS = {
        "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
        "12:00", "12:30", "14:00", "14:30", "15:00", "15:30",
        "16:00", "16:30", "17:00", "17:30"
    };

    public List<String> getAvailableTimeSlots(Long doctorId, String date) {
        try {
            LocalDate appointmentDate = LocalDate.parse(date);
            List<String> availableSlots = new ArrayList<>();
            
            // Get all appointments for this doctor on this date
            LocalDateTime startOfDay = appointmentDate.atStartOfDay();
            LocalDateTime endOfDay = appointmentDate.atTime(LocalTime.MAX);
            List<DoctorAppointment> existingAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDateBetween(doctorId, startOfDay, endOfDay);
            
            // Extract booked time slots
            List<String> bookedSlots = new ArrayList<>();
            for (DoctorAppointment appointment : existingAppointments) {
                if (appointment.getAppointmentTime() != null && 
                    !"Cancelled".equalsIgnoreCase(appointment.getStatus())) {
                    bookedSlots.add(appointment.getAppointmentTime());
                }
            }
            
            // Find available slots
            for (String slot : TIME_SLOTS) {
                if (!bookedSlots.contains(slot)) {
                    availableSlots.add(slot);
                }
            }
            
            return availableSlots;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean isTimeSlotAvailable(Long doctorId, String date, String time) {
        try {
            LocalDate appointmentDate = LocalDate.parse(date);
            LocalDateTime startOfDay = appointmentDate.atStartOfDay();
            LocalDateTime endOfDay = appointmentDate.atTime(LocalTime.MAX);
            
            List<DoctorAppointment> existingAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDateBetween(doctorId, startOfDay, endOfDay);
            
            for (DoctorAppointment appointment : existingAppointments) {
                if (time.equals(appointment.getAppointmentTime()) && 
                    !"Cancelled".equalsIgnoreCase(appointment.getStatus())) {
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DoctorAppointment bookAppointment(Long doctorId, Long patientId, String patientName, 
                                           String patientEmail, String contactNumber,
                                           String appointmentDate, String appointmentTime, 
                                           String notes) {
        
        // Validate time slot availability
        if (!isTimeSlotAvailable(doctorId, appointmentDate, appointmentTime)) {
            throw new RuntimeException("Selected time slot is no longer available");
        }
        
        // Parse appointment date and time
        LocalDate date = LocalDate.parse(appointmentDate);
        LocalTime time = LocalTime.parse(appointmentTime);
        LocalDateTime appointmentDateTime = date.atTime(time);
        
        // Create new appointment
        DoctorAppointment appointment = new DoctorAppointment();
        appointment.setDoctorId(doctorId);
        appointment.setPatientId(patientId);
        appointment.setPatientName(patientName);
        appointment.setPatientEmail(patientEmail);
        appointment.setContactNumber(contactNumber);
        appointment.setAppointmentDate(appointmentDateTime);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus("Scheduled");
        appointment.setNotes(notes);
        appointment.setCreatedDate(LocalDateTime.now());
        
        return appointmentRepository.save(appointment);
    }

    public List<String> getAllTimeSlots() {
        List<String> allSlots = new ArrayList<>();
        for (String slot : TIME_SLOTS) {
            allSlots.add(slot);
        }
        return allSlots;
    }

    public String formatTimeSlot(String timeSlot) {
        try {
            LocalTime time = LocalTime.parse(timeSlot);
            return time.format(DateTimeFormatter.ofPattern("h:mm a"));
        } catch (Exception e) {
            return timeSlot;
        }
    }
}