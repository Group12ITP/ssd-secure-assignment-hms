package com.example.test.Controller;

import com.example.test.Model.Prescription;
import com.example.test.Repository.PrescriptionRepository;
import com.example.test.Service.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class TestController {

    @Autowired
    private SampleDataService sampleDataService;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Application is running successfully!";
    }

    @GetMapping("/test-page")
    public String testPage() {
        return "test";
    }

    @GetMapping("/test-db")
    @ResponseBody
    public String testDatabase() {
        try {
            // Test database connection by trying to count prescriptions
            long prescriptionCount = prescriptionRepository.count();
            return "Database connection test - OK. Prescription count: " + prescriptionCount;
        } catch (Exception e) {
            return "Database error: " + e.getMessage();
        }
    }

    @GetMapping("/test-prescription-save")
    @ResponseBody
    public String testPrescriptionSave() {
        try {
            // Create a test prescription
            Prescription testPrescription = new Prescription();
            testPrescription.setDoctorId(1L);
            testPrescription.setPatientId(1L);
            testPrescription.setAppointmentId(1L);
            testPrescription.setDiagnosis("Test diagnosis");
            testPrescription.setSymptoms("Test symptoms");
            testPrescription.setNotes("Test notes");
            testPrescription.setPrescriptionDate(LocalDateTime.now());
            testPrescription.setStatus("Test");
            
            Prescription saved = prescriptionRepository.save(testPrescription);
            return "Test prescription saved successfully! ID: " + saved.getPrescriptionId();
        } catch (Exception e) {
            return "Error saving test prescription: " + e.getMessage();
        }
    }

    @GetMapping("/create-sample-appointments")
    @ResponseBody
    public String createSampleAppointments() {
        try {
            sampleDataService.createSampleAppointments();
            return "Sample appointments created successfully!";
        } catch (Exception e) {
            return "Error creating sample appointments: " + e.getMessage();
        }
    }
}
