package com.example.test.Controller;

import com.example.test.Model.Doctor;
import com.example.test.Model.DoctorAppointment;
import com.example.test.Model.Prescription;
import com.example.test.Service.DoctorService;
import com.example.test.Service.DoctorAppointmentService;
import com.example.test.Service.PrescriptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private DoctorAppointmentService appointmentService;

    @Mock
    private PrescriptionService prescriptionService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private DoctorController doctorController;

    private Doctor testDoctor;
    private DoctorAppointment testAppointment;
    private Prescription testPrescription;

    @BeforeEach
    void setUp() {
        testDoctor = new Doctor();
        testDoctor.setDoctorId(1L);
        testDoctor.setUsername("dr_smith");
        testDoctor.setEmail("dr.smith@example.com");
        testDoctor.setFullName("Dr. John Smith");
        testDoctor.setSpecialization("Cardiology");

        testAppointment = new DoctorAppointment();
        testAppointment.setAppointmentId(1L);
        testAppointment.setDoctorId(1L);
        testAppointment.setPatientName("John Doe");
        testAppointment.setAppointmentDate(LocalDateTime.now().plusDays(1));
        testAppointment.setStatus("Scheduled");

        testPrescription = new Prescription();
        testPrescription.setPrescriptionId(1L);
        testPrescription.setDoctorId(1L);
        testPrescription.setPatientId(1L);
        testPrescription.setStatus("Active");

        lenient().when(request.getSession()).thenReturn(session);
        lenient().when(request.getSession(anyBoolean())).thenReturn(session);
    }

    @Test
    void testShowLoginForm() {
        // When
        String result = doctorController.showLoginForm();

        // Then
        assertEquals("doctor/doctor-login", result);
    }

    @Test
    void testShowRegisterForm() {
        // When
        String result = doctorController.showRegistrationForm(model);

        // Then
        assertEquals("doctor/doctor-register", result);
        verify(model).addAttribute(eq("doctor"), any(Doctor.class));
    }

    @Test
    void testRegisterDoctor_Success() {
        // Given
        when(doctorService.registerDoctor(testDoctor)).thenReturn(testDoctor);

        // When
        String result = doctorController.registerDoctor(testDoctor, model);

        // Then
        assertEquals("redirect:/doctor/login", result);
        verify(doctorService).registerDoctor(testDoctor);
        verify(model).addAttribute("success", "Doctor registered successfully!");
    }

    @Test
    void testRegisterDoctor_WithException() {
        // Given
        when(doctorService.registerDoctor(testDoctor))
                .thenThrow(new RuntimeException("Username already exists"));

        // When
        String result = doctorController.registerDoctor(testDoctor, model);

        // Then
        assertEquals("doctor/doctor-register", result);
        verify(doctorService).registerDoctor(testDoctor);
        verify(model).addAttribute("error", "Username already exists");
    }

    @Test
    void testLoginDoctor_Success() {
        // Given
        String username = "dr_smith";
        String password = "password123";
        List<DoctorAppointment> appointments = Arrays.asList(testAppointment);
        List<Prescription> prescriptions = Arrays.asList(testPrescription);

        when(doctorService.loginDoctor(username, password)).thenReturn(testDoctor);
        when(appointmentService.getAppointmentsByDoctor(testDoctor.getDoctorId())).thenReturn(appointments);
        when(appointmentService.getTodayAppointments(testDoctor.getDoctorId())).thenReturn(appointments);
        when(appointmentService.countAllAppointments(testDoctor.getDoctorId())).thenReturn(5L);
        when(appointmentService.countTodayAppointments(testDoctor.getDoctorId())).thenReturn(2L);
        when(appointmentService.countByStatus(testDoctor.getDoctorId(), "Scheduled")).thenReturn(3L);
        when(appointmentService.countByStatus(testDoctor.getDoctorId(), "Completed")).thenReturn(2L);
        when(prescriptionService.getPrescriptionsByDoctor(testDoctor.getDoctorId())).thenReturn(prescriptions);

        // When
        String result = doctorController.loginDoctor(username, password, request, model);

        // Then
        assertEquals("redirect:/doctor/dashboard", result);
        verify(doctorService).loginDoctor(username, password);
        verify(session).setAttribute("doctor", testDoctor);
        verify(model).addAttribute("doctor", testDoctor);
        verify(model).addAttribute("appointments", appointments);
        verify(model).addAttribute("todayAppointments", appointments);
        verify(model).addAttribute("totalAppointments", 5L);
        verify(model).addAttribute("todayAppointmentsCount", 2L);
        verify(model).addAttribute("scheduledCount", 3L);
        verify(model).addAttribute("completedCount", 2L);
        verify(model).addAttribute("prescriptions", prescriptions);
    }

    @Test
    void testLoginDoctor_WithException() {
        // Given
        String username = "invalid_user";
        String password = "wrong_password";
        
        when(doctorService.loginDoctor(username, password))
                .thenThrow(new RuntimeException("Invalid username or password."));

        // When
        String result = doctorController.loginDoctor(username, password, request, model);

        // Then
        assertEquals("doctor/doctor-login", result);
        verify(doctorService).loginDoctor(username, password);
        verify(model).addAttribute("error", "Invalid username or password.");
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    void testShowDashboard_WithSessionDoctor() {
        // Given
        List<DoctorAppointment> appointments = Arrays.asList(testAppointment);
        List<Prescription> prescriptions = Arrays.asList(testPrescription);

        when(session.getAttribute("doctor")).thenReturn(testDoctor);
        when(appointmentService.getAppointmentsByDoctor(testDoctor.getDoctorId())).thenReturn(appointments);
        when(appointmentService.getTodayAppointments(testDoctor.getDoctorId())).thenReturn(appointments);
        when(appointmentService.countAllAppointments(testDoctor.getDoctorId())).thenReturn(5L);
        when(appointmentService.countTodayAppointments(testDoctor.getDoctorId())).thenReturn(2L);
        when(appointmentService.countByStatus(testDoctor.getDoctorId(), "Scheduled")).thenReturn(3L);
        when(appointmentService.countByStatus(testDoctor.getDoctorId(), "Completed")).thenReturn(2L);
        when(prescriptionService.getPrescriptionsByDoctor(testDoctor.getDoctorId())).thenReturn(prescriptions);

        // When
        String result = doctorController.showDashboard("Registration successful", session, model);

        // Then
        assertEquals("doctor/doctor-dashboard", result);
        verify(session).getAttribute("doctor");
        verify(model).addAttribute("success", "Registration successful");
        verify(model).addAttribute("doctor", testDoctor);
        verify(model).addAttribute("appointments", appointments);
        verify(model).addAttribute("todayAppointments", appointments);
        verify(model).addAttribute("totalAppointments", 5L);
        verify(model).addAttribute("todayAppointmentsCount", 2L);
        verify(model).addAttribute("scheduledCount", 3L);
        verify(model).addAttribute("completedCount", 2L);
        verify(model).addAttribute("prescriptions", prescriptions);
    }

    @Test
    void testShowDashboard_NoDoctorInSession_RedirectsToLogin() {
        // Given
        when(session.getAttribute("doctor")).thenReturn(null);

        // When
        String result = doctorController.showDashboard(null, session, model);

        // Then
        assertEquals("redirect:/doctor/login", result);
        verify(session).getAttribute("doctor");
        verify(model).addAttribute("error", "Please log in first.");
    }

    @Test
    void testShowDashboard_NoDoctorFound() {
        // Given
        when(session.getAttribute("doctor")).thenReturn(null);

        // When
        String result = doctorController.showDashboard(null, session, model);

        // Then
        assertEquals("redirect:/doctor/login", result);
        verify(session).getAttribute("doctor");
        verify(model).addAttribute("error", "Please log in first.");
        verify(doctorService, never()).getDoctorByUsername(anyString());
    }

    @Test
    void testListAppointments_WithSessionDoctor() {
        // Given
        List<DoctorAppointment> appointments = Arrays.asList(testAppointment);

        when(session.getAttribute("doctor")).thenReturn(testDoctor);
        when(appointmentService.getAppointmentsByDoctor(testDoctor.getDoctorId())).thenReturn(appointments);

        // When
        String result = doctorController.listAppointments(session, model);

        // Then
        assertEquals("doctor/appointments", result);
        verify(session).getAttribute("doctor");
        verify(model).addAttribute("doctor", testDoctor);
        verify(model).addAttribute("appointments", appointments);
    }

    @Test
    void testListAppointments_NoDoctorInSession() {
        // Given
        when(session.getAttribute("doctor")).thenReturn(null);

        // When
        String result = doctorController.listAppointments(session, model);

        // Then
        assertEquals("redirect:/doctor/login", result);
        verify(session).getAttribute("doctor");
        verify(doctorService, never()).getDoctorByUsername(anyString());
    }

    @Test
    void testCalendar_WithSessionDoctor() {
        // Given
        List<DoctorAppointment> appointments = Arrays.asList(testAppointment);

        when(session.getAttribute("doctor")).thenReturn(testDoctor);
        when(appointmentService.getAppointmentsByDoctor(testDoctor.getDoctorId())).thenReturn(appointments);

        // When
        String result = doctorController.calendar(session, model);

        // Then
        assertEquals("doctor/calendar", result);
        verify(session).getAttribute("doctor");
        verify(model).addAttribute("doctor", testDoctor);
        verify(model).addAttribute("appointments", appointments);
    }

    @Test
    void testCalendar_NoDoctorFound() {
        // Given
        when(session.getAttribute("doctor")).thenReturn(null);

        // When
        String result = doctorController.calendar(session, model);

        // Then
        assertEquals("redirect:/doctor/login", result);
        verify(session).getAttribute("doctor");
    }

    @Test
    void testProfile_WithSessionDoctor() {
        // Given
        when(session.getAttribute("doctor")).thenReturn(testDoctor);

        // When
        String result = doctorController.profile(session, model);

        // Then
        assertEquals("doctor/profile", result);
        verify(session).getAttribute("doctor");
        verify(model).addAttribute("doctor", testDoctor);
    }

    @Test
    void testProfile_NoDoctorFound() {
        // Given
        when(session.getAttribute("doctor")).thenReturn(null);

        // When
        String result = doctorController.profile(session, model);

        // Then
        assertEquals("redirect:/doctor/login", result);
        verify(session).getAttribute("doctor");
    }

    @Test
    void testCompleteAppointment_Success() {
        // Given
        Long appointmentId = 1L;
        when(appointmentService.updateAppointmentStatus(appointmentId, "Completed")).thenReturn(testAppointment);

        // When
        String result = doctorController.completeAppointment(appointmentId, model);

        // Then
        assertEquals("redirect:/doctor/dashboard", result);
        verify(appointmentService).updateAppointmentStatus(appointmentId, "Completed");
        verify(model).addAttribute("success", "Appointment marked as completed!");
    }

    @Test
    void testCompleteAppointment_WithException() {
        // Given
        Long appointmentId = 999L;
        when(appointmentService.updateAppointmentStatus(appointmentId, "Completed"))
                .thenThrow(new RuntimeException("Appointment not found"));

        // When
        String result = doctorController.completeAppointment(appointmentId, model);

        // Then
        assertEquals("redirect:/doctor/dashboard", result);
        verify(appointmentService).updateAppointmentStatus(appointmentId, "Completed");
        verify(model).addAttribute("error", "Failed to update appointment status: Appointment not found");
    }

    @Test
    void testCreatePrescriptionForAppointment_AppointmentFound() {
        // Given
        Long appointmentId = 1L;
        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(testAppointment);

        // When
        String result = doctorController.createPrescriptionForAppointment(appointmentId, model);

        // Then
        assertEquals("redirect:/prescription/create/1", result);
        verify(appointmentService).getAppointmentById(appointmentId);
        verify(model).addAttribute("appointment", testAppointment);
        verify(model).addAttribute(eq("prescription"), any(Prescription.class));
    }

    @Test
    void testCreatePrescriptionForAppointment_AppointmentNotFound() {
        // Given
        Long appointmentId = 999L;
        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(null);

        // When
        String result = doctorController.createPrescriptionForAppointment(appointmentId, model);

        // Then
        assertEquals("redirect:/doctor/dashboard", result);
        verify(appointmentService).getAppointmentById(appointmentId);
        verify(model).addAttribute("error", "Appointment not found");
    }

    @Test
    void testShowDashboard_WithNullSuccessMessage() {
        // Given
        List<DoctorAppointment> appointments = Arrays.asList(testAppointment);
        List<Prescription> prescriptions = Arrays.asList(testPrescription);

        when(session.getAttribute("doctor")).thenReturn(testDoctor);
        when(appointmentService.getAppointmentsByDoctor(testDoctor.getDoctorId())).thenReturn(appointments);
        when(appointmentService.getTodayAppointments(testDoctor.getDoctorId())).thenReturn(appointments);
        when(appointmentService.countAllAppointments(testDoctor.getDoctorId())).thenReturn(5L);
        when(appointmentService.countTodayAppointments(testDoctor.getDoctorId())).thenReturn(2L);
        when(appointmentService.countByStatus(testDoctor.getDoctorId(), "Scheduled")).thenReturn(3L);
        when(appointmentService.countByStatus(testDoctor.getDoctorId(), "Completed")).thenReturn(2L);
        when(prescriptionService.getPrescriptionsByDoctor(testDoctor.getDoctorId())).thenReturn(prescriptions);

        // When
        String result = doctorController.showDashboard(null, session, model);

        // Then
        assertEquals("doctor/doctor-dashboard", result);
        verify(model, never()).addAttribute(eq("success"), anyString());
    }

    @Test
    void testRegisterDoctor_WithNullDoctor() {
        // Given
        Doctor nullDoctor = null;
        when(doctorService.registerDoctor(nullDoctor))
                .thenThrow(new RuntimeException("Doctor cannot be null"));

        // When
        String result = doctorController.registerDoctor(nullDoctor, model);

        // Then
        assertEquals("doctor/doctor-register", result);
        verify(doctorService).registerDoctor(nullDoctor);
        verify(model).addAttribute("error", "Doctor cannot be null");
    }
}
