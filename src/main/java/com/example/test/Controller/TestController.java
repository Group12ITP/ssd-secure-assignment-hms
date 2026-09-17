package com.example.test.Controller;

import com.example.test.Model.Prescription;
import com.example.test.Repository.PrescriptionRepository;
import com.example.test.Service.SampleDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@Profile("dev")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private SampleDataService sampleDataService;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Application is running in development mode.";
    }

    @GetMapping("/test-page")
    public String testPage() {
        return "test";
    }

    @GetMapping("/test-db")
    @ResponseBody
    public String testDatabase() {
        try {
            long prescriptionCount = prescriptionRepository.count();
            return "Database connection test - OK. Total count: " + prescriptionCount;
        } catch (Exception e) {
            logger.error("Database connection test error", e);
            return "Database connection test failed. Check server logs.";
        }
    }

    @GetMapping("/test-prescription-save")
    @ResponseBody
    public String testPrescriptionSave() {
        try {
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
            logger.error("Error saving test prescription", e);
            return "Error saving test prescription. Check server logs.";
        }
    }

    @GetMapping("/create-sample-appointments")
    @ResponseBody
    public String createSampleAppointments() {
        try {
            sampleDataService.createSampleAppointments();
            return "Sample appointments created successfully!";
        } catch (Exception e) {
            logger.error("Error creating sample appointments", e);
            return "Error creating sample appointments. Check server logs.";
        }
    }
}
