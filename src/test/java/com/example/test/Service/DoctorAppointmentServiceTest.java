package com.example.test.Service;

import com.example.test.Model.DoctorAppointment;
import com.example.test.Repository.DoctorAppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorAppointmentServiceTest {

    @Mock
    private DoctorAppointmentRepository appointmentRepository;

    @InjectMocks
    private DoctorAppointmentService appointmentService;

    private DoctorAppointment testAppointment1;
    private DoctorAppointment testAppointment2;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        testDateTime = LocalDateTime.of(2024, 1, 15, 10, 30);
        
        testAppointment1 = new DoctorAppointment();
        testAppointment1.setAppointmentId(1L);
        testAppointment1.setDoctorId(1L);
        testAppointment1.setPatientId(1L);
        testAppointment1.setPatientName("John Doe");
        testAppointment1.setPatientEmail("john.doe@example.com");
        testAppointment1.setContactNumber("1234567890");
        testAppointment1.setAppointmentDate(testDateTime);
        testAppointment1.setAppointmentTime("10:30");
        testAppointment1.setStatus("Scheduled");
        testAppointment1.setNotes("Regular checkup");
        testAppointment1.setCreatedDate(testDateTime.minusDays(1));

        testAppointment2 = new DoctorAppointment();
        testAppointment2.setAppointmentId(2L);
        testAppointment2.setDoctorId(1L);
        testAppointment2.setPatientId(2L);
        testAppointment2.setPatientName("Jane Smith");
        testAppointment2.setPatientEmail("jane.smith@example.com");
        testAppointment2.setContactNumber("0987654321");
        testAppointment2.setAppointmentDate(testDateTime.plusDays(1));
        testAppointment2.setAppointmentTime("14:00");
        testAppointment2.setStatus("Completed");
        testAppointment2.setNotes("Follow-up appointment");
        testAppointment2.setCreatedDate(testDateTime.minusDays(2));
    }

    @Test
    void testGetAppointmentsByDoctor_Success() {
        // Given
        Long doctorId = 1L;
        List<DoctorAppointment> expectedAppointments = Arrays.asList(testAppointment1, testAppointment2);
        when(appointmentRepository.findByDoctorId(doctorId)).thenReturn(expectedAppointments);

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByDoctor(doctorId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testAppointment1));
        assertTrue(result.contains(testAppointment2));
        verify(appointmentRepository).findByDoctorId(doctorId);
    }

    @Test
    void testGetAppointmentsByDoctor_EmptyList() {
        // Given
        Long doctorId = 999L;
        when(appointmentRepository.findByDoctorId(doctorId)).thenReturn(Arrays.asList());

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByDoctor(doctorId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(appointmentRepository).findByDoctorId(doctorId);
    }

    @Test
    void testGetTodayAppointments_Success() {
        // Given
        Long doctorId = 1L;
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        List<DoctorAppointment> expectedAppointments = Arrays.asList(testAppointment1);
        
        when(appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end))
                .thenReturn(expectedAppointments);

        // When
        List<DoctorAppointment> result = appointmentService.getTodayAppointments(doctorId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAppointment1, result.get(0));
        verify(appointmentRepository).findByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    @Test
    void testGetTodayAppointments_EmptyList() {
        // Given
        Long doctorId = 999L;
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        
        when(appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end))
                .thenReturn(Arrays.asList());

        // When
        List<DoctorAppointment> result = appointmentService.getTodayAppointments(doctorId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(appointmentRepository).findByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    @Test
    void testCountAllAppointments_Success() {
        // Given
        Long doctorId = 1L;
        long expectedCount = 5L;
        when(appointmentRepository.countByDoctorId(doctorId)).thenReturn(expectedCount);

        // When
        long result = appointmentService.countAllAppointments(doctorId);

        // Then
        assertEquals(expectedCount, result);
        verify(appointmentRepository).countByDoctorId(doctorId);
    }

    @Test
    void testCountAllAppointments_ZeroCount() {
        // Given
        Long doctorId = 999L;
        when(appointmentRepository.countByDoctorId(doctorId)).thenReturn(0L);

        // When
        long result = appointmentService.countAllAppointments(doctorId);

        // Then
        assertEquals(0L, result);
        verify(appointmentRepository).countByDoctorId(doctorId);
    }

    @Test
    void testCountTodayAppointments_Success() {
        // Given
        Long doctorId = 1L;
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        long expectedCount = 3L;
        
        when(appointmentRepository.countByDoctorIdAndAppointmentDateBetween(doctorId, start, end))
                .thenReturn(expectedCount);

        // When
        long result = appointmentService.countTodayAppointments(doctorId);

        // Then
        assertEquals(expectedCount, result);
        verify(appointmentRepository).countByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    @Test
    void testCountTodayAppointments_ZeroCount() {
        // Given
        Long doctorId = 999L;
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        
        when(appointmentRepository.countByDoctorIdAndAppointmentDateBetween(doctorId, start, end))
                .thenReturn(0L);

        // When
        long result = appointmentService.countTodayAppointments(doctorId);

        // Then
        assertEquals(0L, result);
        verify(appointmentRepository).countByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    @Test
    void testCountByStatus_Success() {
        // Given
        Long doctorId = 1L;
        String status = "Scheduled";
        long expectedCount = 2L;
        when(appointmentRepository.countByDoctorIdAndStatus(doctorId, status)).thenReturn(expectedCount);

        // When
        long result = appointmentService.countByStatus(doctorId, status);

        // Then
        assertEquals(expectedCount, result);
        verify(appointmentRepository).countByDoctorIdAndStatus(doctorId, status);
    }

    @Test
    void testCountByStatus_ZeroCount() {
        // Given
        Long doctorId = 999L;
        String status = "Cancelled";
        when(appointmentRepository.countByDoctorIdAndStatus(doctorId, status)).thenReturn(0L);

        // When
        long result = appointmentService.countByStatus(doctorId, status);

        // Then
        assertEquals(0L, result);
        verify(appointmentRepository).countByDoctorIdAndStatus(doctorId, status);
    }

    @Test
    void testUpdateAppointmentStatus_Success() {
        // Given
        Long appointmentId = 1L;
        String newStatus = "Completed";
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment1));
        when(appointmentRepository.save(any(DoctorAppointment.class))).thenReturn(testAppointment1);

        // When
        DoctorAppointment result = appointmentService.updateAppointmentStatus(appointmentId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(appointmentRepository).findById(appointmentId);
        verify(appointmentRepository).save(any(DoctorAppointment.class));
    }

    @Test
    void testUpdateAppointmentStatus_AppointmentNotFound() {
        // Given
        Long appointmentId = 999L;
        String newStatus = "Completed";
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // When
        DoctorAppointment result = appointmentService.updateAppointmentStatus(appointmentId, newStatus);

        // Then
        assertNull(result);
        verify(appointmentRepository).findById(appointmentId);
        verify(appointmentRepository, never()).save(any(DoctorAppointment.class));
    }

    @Test
    void testGetAppointmentById_Success() {
        // Given
        Long appointmentId = 1L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment1));

        // When
        DoctorAppointment result = appointmentService.getAppointmentById(appointmentId);

        // Then
        assertNotNull(result);
        assertEquals(testAppointment1, result);
        verify(appointmentRepository).findById(appointmentId);
    }

    @Test
    void testGetAppointmentById_NotFound() {
        // Given
        Long appointmentId = 999L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // When
        DoctorAppointment result = appointmentService.getAppointmentById(appointmentId);

        // Then
        assertNull(result);
        verify(appointmentRepository).findById(appointmentId);
    }

    @Test
    void testSaveAppointment_Success() {
        // Given
        when(appointmentRepository.save(any(DoctorAppointment.class))).thenReturn(testAppointment1);

        // When
        DoctorAppointment result = appointmentService.saveAppointment(testAppointment1);

        // Then
        assertNotNull(result);
        assertEquals(testAppointment1, result);
        verify(appointmentRepository).save(testAppointment1);
    }

    @Test
    void testGetAppointmentsByStatus_Success() {
        // Given
        String status = "Scheduled";
        List<DoctorAppointment> expectedAppointments = Arrays.asList(testAppointment1);
        when(appointmentRepository.findByStatus(status)).thenReturn(expectedAppointments);

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByStatus(status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAppointment1, result.get(0));
        verify(appointmentRepository).findByStatus(status);
    }

    @Test
    void testGetAppointmentsByStatus_EmptyList() {
        // Given
        String status = "Cancelled";
        when(appointmentRepository.findByStatus(status)).thenReturn(Arrays.asList());

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByStatus(status);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(appointmentRepository).findByStatus(status);
    }

    @Test
    void testGetAppointmentsByPatient_Success() {
        // Given
        Long patientId = 1L;
        List<DoctorAppointment> expectedAppointments = Arrays.asList(testAppointment1);
        when(appointmentRepository.findByPatientId(patientId)).thenReturn(expectedAppointments);

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByPatient(patientId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAppointment1, result.get(0));
        verify(appointmentRepository).findByPatientId(patientId);
    }

    @Test
    void testGetAppointmentsByPatient_EmptyList() {
        // Given
        Long patientId = 999L;
        when(appointmentRepository.findByPatientId(patientId)).thenReturn(Arrays.asList());

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByPatient(patientId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(appointmentRepository).findByPatientId(patientId);
    }

    @Test
    void testUpdateAppointmentStatus_WithNullStatus() {
        // Given
        Long appointmentId = 1L;
        String newStatus = null;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment1));
        when(appointmentRepository.save(any(DoctorAppointment.class))).thenReturn(testAppointment1);

        // When
        DoctorAppointment result = appointmentService.updateAppointmentStatus(appointmentId, newStatus);

        // Then
        assertNotNull(result);
        assertNull(result.getStatus());
        verify(appointmentRepository).findById(appointmentId);
        verify(appointmentRepository).save(any(DoctorAppointment.class));
    }

    @Test
    void testUpdateAppointmentStatus_WithEmptyStatus() {
        // Given
        Long appointmentId = 1L;
        String newStatus = "";
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment1));
        when(appointmentRepository.save(any(DoctorAppointment.class))).thenReturn(testAppointment1);

        // When
        DoctorAppointment result = appointmentService.updateAppointmentStatus(appointmentId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals("", result.getStatus());
        verify(appointmentRepository).findById(appointmentId);
        verify(appointmentRepository).save(any(DoctorAppointment.class));
    }

    @Test
    void testSaveAppointment_WithNullAppointment() {
        // Given
        when(appointmentRepository.save(null)).thenReturn(null);

        // When
        DoctorAppointment result = appointmentService.saveAppointment(null);

        // Then
        assertNull(result);
        verify(appointmentRepository).save(null);
    }

    @Test
    void testGetAppointmentsByStatus_WithNullStatus() {
        // Given
        String status = null;
        when(appointmentRepository.findByStatus(status)).thenReturn(Arrays.asList());

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByStatus(status);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(appointmentRepository).findByStatus(status);
    }

    @Test
    void testGetAppointmentsByStatus_WithEmptyStatus() {
        // Given
        String status = "";
        when(appointmentRepository.findByStatus(status)).thenReturn(Arrays.asList());

        // When
        List<DoctorAppointment> result = appointmentService.getAppointmentsByStatus(status);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(appointmentRepository).findByStatus(status);
    }

    @Test
    void testCountByStatus_WithNullStatus() {
        // Given
        Long doctorId = 1L;
        String status = null;
        when(appointmentRepository.countByDoctorIdAndStatus(doctorId, status)).thenReturn(0L);

        // When
        long result = appointmentService.countByStatus(doctorId, status);

        // Then
        assertEquals(0L, result);
        verify(appointmentRepository).countByDoctorIdAndStatus(doctorId, status);
    }

    @Test
    void testCountByStatus_WithEmptyStatus() {
        // Given
        Long doctorId = 1L;
        String status = "";
        when(appointmentRepository.countByDoctorIdAndStatus(doctorId, status)).thenReturn(0L);

        // When
        long result = appointmentService.countByStatus(doctorId, status);

        // Then
        assertEquals(0L, result);
        verify(appointmentRepository).countByDoctorIdAndStatus(doctorId, status);
    }
}
