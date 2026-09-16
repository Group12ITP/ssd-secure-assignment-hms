package com.example.test.Controller;

import com.example.test.Model.Pharmacist;
import com.example.test.Model.Patient;
import com.example.test.Model.Prescription;
import com.example.test.Model.DoctorAppointment;
import com.example.test.Model.Prescription;
import com.example.test.Service.PharmacistService;
import com.example.test.Service.PrescriptionService;
import com.example.test.Service.PatientService;
import com.example.test.Service.DoctorAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pharmacist")
public class PharmacistController {

    @Autowired
    private PharmacistService pharmacistService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorAppointmentService doctorAppointmentService;


    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(required = false) String username,
                                HttpServletRequest request,
                                Model model) {
        if (username == null || username.isBlank()) {
            Object fromSession = request.getSession().getAttribute("pharmacistUsername");
            if (fromSession instanceof String) {
                username = (String) fromSession;
            }
        }

        Pharmacist pharmacist = pharmacistService.getPharmacistByUsername(username);
        if (pharmacist == null) {
            model.addAttribute("error", "Pharmacist not found.");
            return "pharmacist/pharmacist-login";
        }

        List<Prescription> orders = prescriptionService.getActivePrescriptionsForPharmacist();
        long pending = prescriptionService.countPrescriptionsByStatus("Active");
        long completedToday = prescriptionService.countCompletedToday();
        long urgent = prescriptionService.getUrgentPrescriptions().size();
        model.addAttribute("pharmacist", pharmacist);
        model.addAttribute("orders", orders);
        model.addAttribute("pendingCount", pending);
        model.addAttribute("completedToday", completedToday);
        model.addAttribute("urgentCount", urgent);
        return "pharmacist/pharmacist-dashboard";
    }

    @GetMapping("/inventory")
    public String showInventory(Model model) {
        // Use MedicineController view, but provide a pharmacist-specific route
        return "redirect:/medicine/list";
    }

    @GetMapping("/patients")
    public String listPatientsWithOrders(Model model) {
        List<Prescription> completed = prescriptionService.getPrescriptionsByStatus("Completed");
        java.util.Map<Long, java.util.List<Prescription>> byPatient = new java.util.HashMap<>();
        for (Prescription p : completed) {
            byPatient.computeIfAbsent(p.getPatientId(), k -> new java.util.ArrayList<>()).add(p);
        }

        java.util.List<java.util.Map<String, Object>> patientCards = new java.util.ArrayList<>();
        for (java.util.Map.Entry<Long, java.util.List<Prescription>> entry : byPatient.entrySet()) {
            Long patientId = entry.getKey();
            java.util.List<Prescription> prescriptions = entry.getValue();
            prescriptions.sort((a, b) -> b.getPrescriptionDate().compareTo(a.getPrescriptionDate()));
            Prescription latest = prescriptions.get(0);

            Patient patient = null;
            try { patient = patientService.getPatientById(patientId); } catch (Exception ignored) {}
            DoctorAppointment appointment = null;
            if (latest != null && latest.getAppointmentId() != null) {
                appointment = doctorAppointmentService.getAppointmentById(latest.getAppointmentId());
            }

            java.util.Map<String, Object> card = new java.util.HashMap<>();
            card.put("patient", patient);
            card.put("latestPrescription", latest);
            card.put("appointment", appointment);
            card.put("prescriptionCount", prescriptions.size());
            patientCards.add(card);
        }

        model.addAttribute("patientCards", patientCards);
        return "patient/patients-with-orders";
    }
}
