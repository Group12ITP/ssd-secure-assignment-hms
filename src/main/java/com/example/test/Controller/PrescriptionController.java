package com.example.test.Controller;

import com.example.test.Model.Medicine;
import com.example.test.Model.Prescription;
import com.example.test.Model.PrescriptionMedicine;
import com.example.test.Service.MedicineService;
import com.example.test.Service.PrescriptionService;
import com.example.test.Service.PrescriptionMedicineService;
import com.example.test.Service.DoctorAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private DoctorAppointmentService appointmentService;

    @Autowired
    private PrescriptionMedicineService prescriptionMedicineService;

    @GetMapping("/create")
    public String showCreatePrescriptionForm(Model model) {
        // Redirect to doctor dashboard if no appointment ID provided
        model.addAttribute("error", "Please select an appointment to create a prescription");
        return "redirect:/doctor/dashboard";
    }

    @GetMapping("/create/{appointmentId}")
    public String showCreatePrescriptionForm(@PathVariable Long appointmentId, Model model) {
        try {
            // Get appointment details
            var appointment = appointmentService.getAppointmentById(appointmentId);
            if (appointment == null) {
                model.addAttribute("error", "Appointment not found");
                return "redirect:/doctor/dashboard";
            }

            // Get medicine categories
            List<String> categories = medicineService.getAllCategories();

            model.addAttribute("appointment", appointment);
            model.addAttribute("categories", categories);
            // Pre-populate prescription with required IDs so hidden fields bind properly
            Prescription prescription = new Prescription();
            prescription.setDoctorId(appointment.getDoctorId());
            prescription.setPatientId(appointment.getPatientId());
            prescription.setAppointmentId(appointment.getAppointmentId());
            prescription.setStatus("Active");
            prescription.setIsUrgent(false);
            model.addAttribute("prescription", prescription);
            return "doctor/create-prescription";
        } catch (Exception e) {
            System.err.println("Error loading prescription form: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading prescription form: " + e.getMessage());
            return "redirect:/doctor/dashboard";
        }
    }

    @PostMapping("/create")
    public String createPrescription(@ModelAttribute Prescription prescription,
                                     @RequestParam(value = "medicineIds", required = false) Long[] medicineIds,
                                     @RequestParam(value = "dosages", required = false) String[] dosages,
                                     @RequestParam(value = "frequencies", required = false) String[] frequencies,
                                     @RequestParam(value = "durations", required = false) String[] durations,
                                     @RequestParam(value = "instructions", required = false) String[] instructions,
                                     Model model,
                                     HttpServletRequest request) {
        try {
            System.out.println("=== PRESCRIPTION CREATION DEBUG ===");
            System.out.println("Prescription data received:");
            System.out.println("Doctor ID: " + prescription.getDoctorId());
            System.out.println("Patient ID: " + prescription.getPatientId());
            System.out.println("Appointment ID: " + prescription.getAppointmentId());
            System.out.println("Diagnosis: " + prescription.getDiagnosis());
            System.out.println("Symptoms: " + prescription.getSymptoms());
            System.out.println("Notes: " + prescription.getNotes());
            System.out.println("Status: " + prescription.getStatus());
            System.out.println("Is Urgent: " + prescription.getIsUrgent());

            // Validate required fields
            if (prescription.getDiagnosis() == null || prescription.getDiagnosis().trim().isEmpty()) {
                model.addAttribute("error", "Diagnosis is required");
                // Return to form with current data
                var appointment = appointmentService.getAppointmentById(prescription.getAppointmentId());
                List<String> categories = medicineService.getAllCategories();
                model.addAttribute("appointment", appointment);
                model.addAttribute("categories", categories);
                return "doctor/create-prescription";
            }

            // Set default values if null
            if (prescription.getStatus() == null) {
                prescription.setStatus("Active");
            }
            if (prescription.getIsUrgent() == null) {
                prescription.setIsUrgent(false);
            }

            // Serialize selected medicines to JSON and set medicineDetails before save
            if (medicineIds != null && medicineIds.length > 0) {
                try {
                    List<Map<String, Object>> medicineDetailsList = new ArrayList<>();
                    for (int i = 0; i < medicineIds.length; i++) {
                        Map<String, Object> entry = new HashMap<>();
                        entry.put("medicineId", medicineIds[i]);
                        entry.put("dosage", (dosages != null && i < dosages.length) ? dosages[i] : "1 tablet");
                        entry.put("frequency", (frequencies != null && i < frequencies.length) ? frequencies[i] : "twice daily");
                        entry.put("duration", (durations != null && i < durations.length) ? durations[i] : "7 days");
                        entry.put("instructions", (instructions != null && i < instructions.length) ? instructions[i] : "");
                        // Optional enrichment with medicine name/strength
                        medicineService.getMedicineById(medicineIds[i]).ifPresent(m -> {
                            entry.put("medicineName", m.getMedicineName());
                            entry.put("genericName", m.getGenericName());
                            entry.put("strength", m.getStrength());
                            entry.put("dosageForm", m.getDosageForm());
                            entry.put("unitPrice", m.getUnitPrice());
                        });
                        medicineDetailsList.add(entry);
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    prescription.setMedicineDetails(mapper.writeValueAsString(medicineDetailsList));
                } catch (Exception jsonEx) {
                    System.err.println("Error serializing medicine details: " + jsonEx.getMessage());
                }
            }

            // Create the prescription
            Prescription savedPrescription = prescriptionService.createPrescription(prescription);
            System.out.println("Saved prescription ID: " + savedPrescription.getPrescriptionId());

            // Save prescription medicines if provided
            if (medicineIds != null && medicineIds.length > 0) {
                System.out.println("Processing " + medicineIds.length + " medicines...");
                for (int i = 0; i < medicineIds.length; i++) {
                    try {
                        String dosage = (dosages != null && i < dosages.length && dosages[i] != null && !dosages[i].trim().isEmpty())
                                ? dosages[i] : "1 tablet";
                        String frequency = (frequencies != null && i < frequencies.length && frequencies[i] != null && !frequencies[i].trim().isEmpty())
                                ? frequencies[i] : "twice daily";
                        String duration = (durations != null && i < durations.length && durations[i] != null && !durations[i].trim().isEmpty())
                                ? durations[i] : "7 days";
                        String instruction = (instructions != null && i < instructions.length && instructions[i] != null)
                                ? instructions[i] : "";

                        System.out.println("Creating medicine " + (i+1) + ": ID=" + medicineIds[i] + ", dosage=" + dosage);

                        PrescriptionMedicine pm = prescriptionMedicineService.createPrescriptionMedicine(
                                savedPrescription.getPrescriptionId(),
                                medicineIds[i],
                                dosage,
                                frequency,
                                duration,
                                instruction
                        );
                        System.out.println("Created prescription medicine: " + pm.getPrescriptionMedicineId());
                    } catch (Exception e) {
                        System.err.println("Error creating prescription medicine " + (i+1) + ": " + e.getMessage());
                        e.printStackTrace();
                        // Continue with other medicines
                    }
                }

                // Calculate and update total amount
                try {
                    Double totalAmount = prescriptionMedicineService.getTotalPriceByPrescription(savedPrescription.getPrescriptionId());
                    System.out.println("Total amount calculated: " + totalAmount);
                    if (totalAmount != null) {
                        savedPrescription.setTotalAmount(totalAmount);
                        prescriptionService.updatePrescription(savedPrescription);
                        System.out.println("Updated prescription with total amount: " + totalAmount);
                    }
                } catch (Exception e) {
                    System.err.println("Error calculating total amount: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("No medicines provided for prescription");
            }

            // Update appointment status to completed
            try {
                appointmentService.updateAppointmentStatus(prescription.getAppointmentId(), "Completed");
                System.out.println("Updated appointment status to Completed");
            } catch (Exception e) {
                System.err.println("Error updating appointment status: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("=== PRESCRIPTION CREATION SUCCESS ===");
            return "redirect:/doctor/dashboard?success=Prescription%20created%20successfully";

        } catch (Exception e) {
            System.err.println("=== PRESCRIPTION CREATION ERROR ===");
            System.err.println("Error creating prescription: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to create prescription: " + e.getMessage());

            // Return to the form with error message
            var appointment = appointmentService.getAppointmentById(prescription.getAppointmentId());
            List<String> categories = medicineService.getAllCategories();
            model.addAttribute("appointment", appointment);
            model.addAttribute("categories", categories);
            return "doctor/create-prescription";
        }
    }

    @GetMapping("/medicines/{category}")
    @ResponseBody
    public List<Medicine> getMedicinesByCategory(@PathVariable String category) {
        return medicineService.getPrescriptionMedicinesByCategory(category);
    }

    @GetMapping("/patient/{patientId}")
    public String getPatientPrescriptions(@PathVariable Long patientId, Model model) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patientId);
        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("patientId", patientId);
        return "patient/prescriptions";
    }

    @GetMapping("/details/{prescriptionId}")
    public String getPrescriptionDetails(@PathVariable Long prescriptionId, Model model) {
        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId).orElse(null);
        if (prescription == null) {
            model.addAttribute("error", "Prescription not found");
            return "redirect:/doctor/dashboard";
        }

        List<PrescriptionMedicine> medicines = prescriptionMedicineService.getMedicinesByPrescription(prescriptionId);
        model.addAttribute("prescription", prescription);
        model.addAttribute("medicines", medicines);
        return "prescription/details";
    }

    @GetMapping("/pharmacist/orders")
    public String getPharmacistOrders(@RequestParam(required = false) String status, Model model) {
        List<Prescription> orders;
        if (status == null || status.trim().isEmpty()) {
            orders = prescriptionService.getActivePrescriptionsForPharmacist();
            status = "Active";
        } else {
            orders = prescriptionService.getPrescriptionsByStatus(status);
        }
        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);
        return "pharmacist/scheduled-orders";
    }

    // Lightweight API for pharmacist dashboard to fetch medicines as JSON
    @GetMapping("/api/medicines/{prescriptionId}")
    @ResponseBody
    public List<PrescriptionMedicine> getPrescriptionMedicinesJson(@PathVariable Long prescriptionId) {
        return prescriptionMedicineService.getMedicinesByPrescription(prescriptionId);
    }

    @PostMapping("/update-status/{prescriptionId}")
    public String updatePrescriptionStatus(@PathVariable Long prescriptionId,
                                           @RequestParam String status,
                                           Model model) {
        try {
            prescriptionService.updatePrescriptionStatus(prescriptionId, status);
            model.addAttribute("success", "Prescription status updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update prescription status: " + e.getMessage());
        }
        return "redirect:/prescription/pharmacist/orders";
    }

    // Remove the debug endpoint if you added it, or keep only one
    @GetMapping("/debug")
    @ResponseBody
    public String debugPrescription() {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(1L);
        return "Total prescriptions in database: " + prescriptions.size();
    }

    // CSV report for today's orders
    @GetMapping("/../reports/today.csv")
    public ResponseEntity<String> downloadTodayCsv() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusDays(1);
        List<Prescription> todays = prescriptionService.getPrescriptionsByDateRange(start, end);
        StringBuilder sb = new StringBuilder();
        sb.append("PrescriptionId,PatientId,DoctorId,Status,Diagnosis,Date\n");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Prescription p : todays) {
            sb.append(p.getPrescriptionId()).append(',')
              .append(p.getPatientId()).append(',')
              .append(p.getDoctorId()).append(',')
              .append(p.getStatus()).append(',')
              .append('"').append(p.getDiagnosis() != null ? p.getDiagnosis().replace("\"","'") : "").append('"').append(',')
              .append(p.getPrescriptionDate() != null ? p.getPrescriptionDate().format(fmt) : "").append('\n');
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report-today.csv");
        return ResponseEntity.ok().headers(headers).body(sb.toString());
    }
}