package com.example.test.Service;

import com.example.test.Model.DoctorAppointment;
import com.example.test.Repository.DoctorAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SampleDataService {

    @Autowired
    private DoctorAppointmentRepository appointmentRepository;

    public void createSampleAppointments() {
        try {
            // Check if appointments already exist
            if (appointmentRepository.count() > 0) {
                System.out.println("Sample appointments already exist");
                return;
            }

            // Create sample appointments
            DoctorAppointment appointment1 = new DoctorAppointment();
            appointment1.setDoctorId(1L);
            appointment1.setPatientId(1L);
            appointment1.setPatientName("John Doe");
            appointment1.setPatientEmail("john.doe@email.com");
            appointment1.setContactNumber("123-456-7890");
            appointment1.setAppointmentDate(LocalDateTime.now().plusDays(1));
            appointment1.setStatus("Scheduled");
            appointment1.setNotes("Regular checkup");
            appointmentRepository.save(appointment1);

            DoctorAppointment appointment2 = new DoctorAppointment();
            appointment2.setDoctorId(1L);
            appointment2.setPatientId(2L);
            appointment2.setPatientName("Jane Smith");
            appointment2.setPatientEmail("jane.smith@email.com");
            appointment2.setContactNumber("234-567-8901");
            appointment2.setAppointmentDate(LocalDateTime.now().plusDays(2));
            appointment2.setStatus("Scheduled");
            appointment2.setNotes("Fever and cough");
            appointmentRepository.save(appointment2);

            DoctorAppointment appointment3 = new DoctorAppointment();
            appointment3.setDoctorId(1L);
            appointment3.setPatientId(3L);
            appointment3.setPatientName("Bob Johnson");
            appointment3.setPatientEmail("bob.johnson@email.com");
            appointment3.setContactNumber("345-678-9012");
            appointment3.setAppointmentDate(LocalDateTime.now().plusDays(3));
            appointment3.setStatus("Scheduled");
            appointment3.setNotes("Headache");
            appointmentRepository.save(appointment3);

            System.out.println("Sample appointments created successfully");
        } catch (Exception e) {
            System.err.println("Error creating sample appointments: " + e.getMessage());
        }
    }

    public List<DoctorAppointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<DoctorAppointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
}
