package com.example.test.Controller;


import com.example.test.Model.Doctor;
import com.example.test.Model.DoctorAppointment;
import com.example.test.Model.Prescription;
import com.example.test.Service.DoctorService;
import com.example.test.Service.DoctorAppointmentService;
import com.example.test.Service.PrescriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    @Autowired
    private DoctorAppointmentService appointmentService;

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor/doctor-register";
    }

    @PostMapping("/register")
    public String registerDoctor(@ModelAttribute Doctor doctor, Model model) {
        try {
            doctorService.registerDoctor(doctor);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "redirect:/doctor/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "doctor/doctor-register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "doctor/doctor-login";
    }

    @PostMapping("/login")
    public String loginDoctor(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        try {
            Doctor doctor = doctorService.loginDoctor(username, password);
            model.addAttribute("doctor", doctor);
            // persist in session for subsequent pages (appointments, calendar, profile)
            session.setAttribute("doctor", doctor);
            // Dashboard data
            List<DoctorAppointment> appointments = appointmentService.getAppointmentsByDoctor(doctor.getDoctorId());
            model.addAttribute("appointments", appointments);
            model.addAttribute("todayAppointments", appointmentService.getTodayAppointments(doctor.getDoctorId()));
            model.addAttribute("totalAppointments", appointmentService.countAllAppointments(doctor.getDoctorId()));
            model.addAttribute("todayAppointmentsCount", appointmentService.countTodayAppointments(doctor.getDoctorId()));
            model.addAttribute("scheduledCount", appointmentService.countByStatus(doctor.getDoctorId(), "Scheduled"));
            model.addAttribute("completedCount", appointmentService.countByStatus(doctor.getDoctorId(), "Completed"));
            model.addAttribute("prescriptions", prescriptionService.getPrescriptionsByDoctor(doctor.getDoctorId()));
            return "redirect:/doctor/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "doctor/doctor-login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(required = false) String username,
                                @RequestParam(required = false) String success,
                                HttpSession session, Model model) {

        // Try to get doctor from session or username parameter
        Doctor doctor = (Doctor) session.getAttribute("doctor");

        if (doctor == null && username != null) {
            doctor = doctorService.getDoctorByUsername(username);
        }

        if (doctor == null) {
            model.addAttribute("error", "Please log in first.");
            return "doctor/doctor-login";
        }

        // Store doctor in session for future requests
        session.setAttribute("doctor", doctor);

        // Add success message if present
        if (success != null) {
            model.addAttribute("success", success);
        }

        // Dashboard data
        List<DoctorAppointment> appointments = appointmentService.getAppointmentsByDoctor(doctor.getDoctorId());
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        model.addAttribute("todayAppointments", appointmentService.getTodayAppointments(doctor.getDoctorId()));
        model.addAttribute("totalAppointments", appointmentService.countAllAppointments(doctor.getDoctorId()));
        model.addAttribute("todayAppointmentsCount", appointmentService.countTodayAppointments(doctor.getDoctorId()));
        model.addAttribute("scheduledCount", appointmentService.countByStatus(doctor.getDoctorId(), "Scheduled"));
        model.addAttribute("completedCount", appointmentService.countByStatus(doctor.getDoctorId(), "Completed"));
        model.addAttribute("prescriptions", prescriptionService.getPrescriptionsByDoctor(doctor.getDoctorId()));

        return "doctor/doctor-dashboard";
    }

    @GetMapping("/appointments")
    public String listAppointments(@RequestParam(required = false) String username, HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("doctor");
        if (doctor == null && username != null) {
            doctor = doctorService.getDoctorByUsername(username);
            if (doctor != null) session.setAttribute("doctor", doctor);
        }
        if (doctor == null) return "redirect:/doctor/login";
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointmentService.getAppointmentsByDoctor(doctor.getDoctorId()));
        return "doctor/appointments";
    }

    @GetMapping("/calendar")
    public String calendar(@RequestParam(required = false) String username, HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("doctor");
        if (doctor == null && username != null) {
            doctor = doctorService.getDoctorByUsername(username);
            if (doctor != null) session.setAttribute("doctor", doctor);
        }
        if (doctor == null) return "redirect:/doctor/login";
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointmentService.getAppointmentsByDoctor(doctor.getDoctorId()));
        return "doctor/calendar";
    }

    @GetMapping("/profile")
    public String profile(@RequestParam(required = false) String username, HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("doctor");
        if (doctor == null && username != null) {
            doctor = doctorService.getDoctorByUsername(username);
            if (doctor != null) session.setAttribute("doctor", doctor);
        }
        if (doctor == null) return "redirect:/doctor/login";
        model.addAttribute("doctor", doctor);
        return "doctor/profile";
    }

    @PostMapping("/appointment/{appointmentId}/complete")
    public String completeAppointment(@PathVariable Long appointmentId, Model model) {
        try {
            appointmentService.updateAppointmentStatus(appointmentId, "Completed");
            model.addAttribute("success", "Appointment marked as completed!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update appointment status: " + e.getMessage());
        }
        return "redirect:/doctor/dashboard";
    }

    @GetMapping("/appointment/{appointmentId}/prescription")
    public String createPrescriptionForAppointment(@PathVariable Long appointmentId, Model model) {
        var appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            model.addAttribute("error", "Appointment not found");
            return "redirect:/doctor/dashboard";
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("prescription", new Prescription());
        return "redirect:/prescription/create/" + appointmentId;
    }

}
