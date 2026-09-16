package com.example.test.Controller;

import com.example.test.Model.Patient;
import com.example.test.Model.Doctor;
import com.example.test.Model.DoctorAppointment;
import com.example.test.Model.Prescription;
import com.example.test.Model.PrescriptionMedicine;
import com.example.test.Service.PatientService;
import com.example.test.Repository.DoctorRepository;
import com.example.test.Repository.PrescriptionRepository;
import com.example.test.Repository.PrescriptionMedicineRepository;
import com.example.test.Service.DoctorAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionMedicineRepository prescriptionMedicineRepository;

    @Autowired
    private DoctorAppointmentService doctorAppointmentService;
    
    // @Autowired
    // private AppointmentBookingService appointmentBookingService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/patient-register";
    }


    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient,
                                  @RequestParam String confirmPassword,
                                  Model model) {
        try {
            // Validate password confirmation
            if (!patient.getPassword().equals(confirmPassword)) {
                model.addAttribute("error", "Passwords do not match!");
                return "patient/patient-register";
            }

            patientService.registerPatient(patient);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "redirect:/patient/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "patient/patient-register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "patient/patient-login";
    }

    @PostMapping("/login")
    public String loginPatient(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        try {
            Patient patient = patientService.loginPatient(username, password);
            session.setAttribute("patient", patient);
            return "redirect:/patient/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "patient/patient-login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Object obj = session.getAttribute("patient");
        if (!(obj instanceof Patient)) {
            model.addAttribute("error", "Please log in first.");
            return "patient/patient-login";
        }

        Patient patient = (Patient) obj;
        model.addAttribute("patient", patient);
        model.addAttribute("pageTitle", "Dashboard");

        // Get all doctors instead of patients
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);

        model.addAttribute("appointments", doctorAppointmentService.getAppointmentsByPatient(patient.getPatientId()));

        return "patient/patient-dashboard";
    }

    @GetMapping("/profile/{id}")
    public String showPatientProfile(@PathVariable Long id, Model model) {
        try {
            Patient patient = patientService.getPatientById(id);
            model.addAttribute("patient", patient);
            model.addAttribute("pageTitle", "Profile");
            return "patient/patient-profile";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/patient/login";
        }
    }

    @PostMapping("/submit-feedback")
    public String submitFeedback(@RequestParam("message") String message,
                                 @RequestParam(value = "rating", required = false) Integer rating,
                                 HttpSession session,
                                 Model model) {
        // Simple placeholder: In real app, persist feedback with patient reference
        Object obj = session.getAttribute("patient");
        if (obj instanceof Patient) {
            Patient patient = (Patient) obj;
            model.addAttribute("patient", patient);
            model.addAttribute("appointments", doctorAppointmentService.getAppointmentsByPatient(patient.getPatientId()));
        }
        model.addAttribute("success", "Thank you for your feedback!");

        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "patient/patient-dashboard";
    }

    @GetMapping("/appointments")
    public String appointments(HttpSession session, Model model) {
        Object obj = session.getAttribute("patient");
        if (!(obj instanceof Patient)) {
            model.addAttribute("error", "Please log in first.");
            return "redirect:/patient/login";
        }

        Patient patient = (Patient) obj;
        model.addAttribute("patient", patient);
        model.addAttribute("pageTitle", "Appointments");
        model.addAttribute("appointments", doctorAppointmentService.getAppointmentsByPatient(patient.getPatientId()));
        return "patient/appointments";
    }

    @GetMapping("/prescriptions")
    public String prescriptions(HttpSession session, Model model) {
        Object obj = session.getAttribute("patient");
        if (!(obj instanceof Patient)) {
            model.addAttribute("error", "Please log in first.");
            return "redirect:/patient/login";
        }

        Patient patient = (Patient) obj;
        List<Prescription> prescriptions = prescriptionRepository.findByPatientIdOrderByPrescriptionDateDesc(patient.getPatientId());
        
        model.addAttribute("patient", patient);
        model.addAttribute("pageTitle", "Prescriptions");
        model.addAttribute("prescriptions", prescriptions);
        return "patient/prescriptions";
    }

    @GetMapping("/medications")
    public String medications(HttpSession session, Model model) {
        Object obj = session.getAttribute("patient");
        if (!(obj instanceof Patient)) {
            model.addAttribute("error", "Please log in first.");
            return "redirect:/patient/login";
        }

        Patient patient = (Patient) obj;
        List<Prescription> prescriptions = prescriptionRepository.findByPatientIdOrderByPrescriptionDateDesc(patient.getPatientId());
        
        // Get all prescription medicines for the patient
        List<PrescriptionMedicine> medications = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            medications.addAll(prescriptionMedicineRepository.findByPrescriptionId(prescription.getPrescriptionId()));
        }
        
        model.addAttribute("patient", patient);
        model.addAttribute("pageTitle", "Medications");
        model.addAttribute("medications", medications);
        model.addAttribute("prescriptions", prescriptions);
        return "patient/medications";
    }

    @GetMapping("/get-available-slots")
    @ResponseBody
    public List<String> getAvailableTimeSlots(@RequestParam Long doctorId, 
                                            @RequestParam String date,
                                            HttpSession session) {
        try {
            Object obj = session.getAttribute("patient");
            if (!(obj instanceof Patient)) {
                return new ArrayList<>();
            }
            // Build full slot list
            List<String> allSlots = new ArrayList<>();
            String[] timeSlots = {
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "14:00", "14:30", "15:00", "15:30",
                "16:00", "16:30", "17:00", "17:30"
            };
            for (String slot : timeSlots) {
                allSlots.add(slot);
            }

            // Determine booked slots for the given doctor and date
            LocalDate targetDate = LocalDate.parse(date);
            List<DoctorAppointment> doctorAppointments = doctorAppointmentService.getAppointmentsByDoctor(doctorId);

            List<String> bookedSlots = new ArrayList<>();
            for (DoctorAppointment appt : doctorAppointments) {
                if (appt.getAppointmentDate() != null
                        && appt.getAppointmentDate().toLocalDate().isEqual(targetDate)
                        && appt.getAppointmentTime() != null
                        && appt.getStatus() != null
                        && !"Cancelled".equalsIgnoreCase(appt.getStatus())) {
                    bookedSlots.add(appt.getAppointmentTime());
                }
            }

            // Available = all - booked
            allSlots.removeAll(bookedSlots);
            return allSlots;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(@RequestParam Long doctorId,
                                  @RequestParam String appointmentDate,
                                  @RequestParam String appointmentTime,
                                  @RequestParam(required = false) String notes,
                                  HttpSession session,
                                  Model model) {
        try {
            Object obj = session.getAttribute("patient");
            if (!(obj instanceof Patient)) {
                model.addAttribute("error", "Please log in first.");
                return "redirect:/patient/login";
            }

            Patient patient = (Patient) obj;
            Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
            
            if (doctor == null) {
                model.addAttribute("error", "Doctor not found.");
                return "redirect:/patient/dashboard";
            }

            // Create appointment directly
            DoctorAppointment appointment = new DoctorAppointment();
            appointment.setDoctorId(doctorId);
            appointment.setPatientId(patient.getPatientId());
            appointment.setPatientName(patient.getFullName());
            appointment.setPatientEmail(patient.getEmail());
            appointment.setContactNumber(patient.getContactNumber());
            
            // Parse appointment date and time
            LocalDate date = LocalDate.parse(appointmentDate);
            LocalTime time = LocalTime.parse(appointmentTime);
            LocalDateTime appointmentDateTime = date.atTime(time);
            appointment.setAppointmentDate(appointmentDateTime);
            appointment.setAppointmentTime(appointmentTime);
            appointment.setStatus("Scheduled");
            appointment.setNotes(notes != null ? notes : "");
            appointment.setCreatedDate(LocalDateTime.now());
            
            // Save appointment
            doctorAppointmentService.saveAppointment(appointment);

            model.addAttribute("success", "Appointment booked successfully for " + 
                appointmentDate + " at " + formatTimeSlot(appointmentTime) + "!");
            
            // Refresh dashboard data
            model.addAttribute("patient", patient);
            List<Doctor> doctors = doctorRepository.findAll();
            model.addAttribute("doctors", doctors);
            model.addAttribute("appointments", doctorAppointmentService.getAppointmentsByPatient(patient.getPatientId()));
            
            return "patient/patient-dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to book appointment: " + e.getMessage());
            return "redirect:/patient/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/patient/login";
    }
    
    private String formatTimeSlot(String timeSlot) {
        try {
            LocalTime time = LocalTime.parse(timeSlot);
            return time.format(DateTimeFormatter.ofPattern("h:mm a"));
        } catch (Exception e) {
            return timeSlot;
        }
    }
}