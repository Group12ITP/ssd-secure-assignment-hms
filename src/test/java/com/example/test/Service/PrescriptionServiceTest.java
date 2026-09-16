package com.example.test.Service;

import com.example.test.Model.Prescription;
import com.example.test.Repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private PrescriptionService prescriptionService;

    private Prescription testPrescription1;
    private Prescription testPrescription2;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        testDateTime = LocalDateTime.of(2024, 1, 15, 10, 30);
        
        testPrescription1 = new Prescription();
        testPrescription1.setPrescriptionId(1L);
        testPrescription1.setPatientId(1L);
        testPrescription1.setDoctorId(1L);
        testPrescription1.setAppointmentId(1L);
        testPrescription1.setPrescriptionDate(testDateTime);
        testPrescription1.setStatus("Active");
        testPrescription1.setNotes("Take with food");
        testPrescription1.setIsUrgent(false);
        testPrescription1.setFollowUpDate(testDateTime.plusDays(7));

        testPrescription2 = new Prescription();
        testPrescription2.setPrescriptionId(2L);
        testPrescription2.setPatientId(2L);
        testPrescription2.setDoctorId(1L);
        testPrescription2.setAppointmentId(2L);
        testPrescription2.setPrescriptionDate(testDateTime.minusDays(1));
        testPrescription2.setStatus("Completed");
        testPrescription2.setNotes("Completed treatment");
        testPrescription2.setIsUrgent(true);
        testPrescription2.setFollowUpDate(testDateTime.plusDays(14));
    }

    @Test
    void testCreatePrescription_Success() {
        // Given
        Prescription newPrescription = new Prescription();
        newPrescription.setPatientId(1L);
        newPrescription.setDoctorId(1L);
        newPrescription.setAppointmentId(1L);
        newPrescription.setNotes("Test prescription");
        
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription prescription = invocation.getArgument(0);
            prescription.setPrescriptionId(1L);
            return prescription;
        });

        // When
        Prescription result = prescriptionService.createPrescription(newPrescription);

        // Then
        assertNotNull(result);
        assertNotNull(result.getPrescriptionDate());
        assertEquals("Active", result.getStatus());
        assertEquals("Test prescription", result.getNotes());
        verify(prescriptionRepository).save(any(Prescription.class));
    }

    @Test
    void testCreatePrescription_WithException() {
        // Given
        Prescription newPrescription = new Prescription();
        when(prescriptionRepository.save(any(Prescription.class))).thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            prescriptionService.createPrescription(newPrescription);
        });

        verify(prescriptionRepository).save(any(Prescription.class));
    }

    @Test
    void testGetPrescriptionsByPatient_Success() {
        // Given
        Long patientId = 1L;
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1);
        when(prescriptionRepository.findByPatientIdOrderByPrescriptionDateDesc(patientId))
                .thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByPatient(patientId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription1, result.get(0));
        verify(prescriptionRepository).findByPatientIdOrderByPrescriptionDateDesc(patientId);
    }

    @Test
    void testGetPrescriptionsByPatient_EmptyList() {
        // Given
        Long patientId = 999L;
        when(prescriptionRepository.findByPatientIdOrderByPrescriptionDateDesc(patientId))
                .thenReturn(Arrays.asList());

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByPatient(patientId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(prescriptionRepository).findByPatientIdOrderByPrescriptionDateDesc(patientId);
    }

    @Test
    void testGetPrescriptionsByDoctor_Success() {
        // Given
        Long doctorId = 1L;
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1, testPrescription2);
        when(prescriptionRepository.findByDoctorIdOrderByPrescriptionDateDesc(doctorId))
                .thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByDoctor(doctorId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testPrescription1));
        assertTrue(result.contains(testPrescription2));
        verify(prescriptionRepository).findByDoctorIdOrderByPrescriptionDateDesc(doctorId);
    }

    @Test
    void testGetActivePrescriptionsForPharmacist_Success() {
        // Given
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1);
        when(prescriptionRepository.findActivePrescriptionsForPharmacist()).thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getActivePrescriptionsForPharmacist();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription1, result.get(0));
        verify(prescriptionRepository).findActivePrescriptionsForPharmacist();
    }

    @Test
    void testGetPrescriptionByAppointment_Success() {
        // Given
        Long appointmentId = 1L;
        when(prescriptionRepository.findByAppointmentId(appointmentId)).thenReturn(testPrescription1);

        // When
        Prescription result = prescriptionService.getPrescriptionByAppointment(appointmentId);

        // Then
        assertNotNull(result);
        assertEquals(testPrescription1, result);
        verify(prescriptionRepository).findByAppointmentId(appointmentId);
    }

    @Test
    void testGetPrescriptionByAppointment_NotFound() {
        // Given
        Long appointmentId = 999L;
        when(prescriptionRepository.findByAppointmentId(appointmentId)).thenReturn(null);

        // When
        Prescription result = prescriptionService.getPrescriptionByAppointment(appointmentId);

        // Then
        assertNull(result);
        verify(prescriptionRepository).findByAppointmentId(appointmentId);
    }

    @Test
    void testGetPrescriptionsByStatus_Success() {
        // Given
        String status = "Active";
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1);
        when(prescriptionRepository.findByStatus(status)).thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByStatus(status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription1, result.get(0));
        verify(prescriptionRepository).findByStatus(status);
    }

    @Test
    void testGetPrescriptionsByPatientAndStatus_Success() {
        // Given
        Long patientId = 1L;
        String status = "Active";
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1);
        when(prescriptionRepository.findByPatientIdAndStatusOrderByPrescriptionDateDesc(patientId, status))
                .thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByPatientAndStatus(patientId, status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription1, result.get(0));
        verify(prescriptionRepository).findByPatientIdAndStatusOrderByPrescriptionDateDesc(patientId, status);
    }

    @Test
    void testGetPrescriptionsByDoctorAndStatus_Success() {
        // Given
        Long doctorId = 1L;
        String status = "Completed";
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription2);
        when(prescriptionRepository.findByDoctorIdAndStatusOrderByPrescriptionDateDesc(doctorId, status))
                .thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByDoctorAndStatus(doctorId, status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription2, result.get(0));
        verify(prescriptionRepository).findByDoctorIdAndStatusOrderByPrescriptionDateDesc(doctorId, status);
    }

    @Test
    void testUpdatePrescriptionStatus_Success() {
        // Given
        Long prescriptionId = 1L;
        String newStatus = "Completed";
        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(testPrescription1));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(testPrescription1);

        // When
        Prescription result = prescriptionService.updatePrescriptionStatus(prescriptionId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(prescriptionRepository).findById(prescriptionId);
        verify(prescriptionRepository).save(any(Prescription.class));
    }

    @Test
    void testUpdatePrescriptionStatus_NotFound() {
        // Given
        Long prescriptionId = 999L;
        String newStatus = "Completed";
        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        // When
        Prescription result = prescriptionService.updatePrescriptionStatus(prescriptionId, newStatus);

        // Then
        assertNull(result);
        verify(prescriptionRepository).findById(prescriptionId);
        verify(prescriptionRepository, never()).save(any(Prescription.class));
    }

    @Test
    void testUpdatePrescription_Success() {
        // Given
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(testPrescription1);

        // When
        Prescription result = prescriptionService.updatePrescription(testPrescription1);

        // Then
        assertNotNull(result);
        assertEquals(testPrescription1, result);
        verify(prescriptionRepository).save(testPrescription1);
    }

    @Test
    void testGetPrescriptionById_Success() {
        // Given
        Long prescriptionId = 1L;
        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(testPrescription1));

        // When
        Optional<Prescription> result = prescriptionService.getPrescriptionById(prescriptionId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testPrescription1, result.get());
        verify(prescriptionRepository).findById(prescriptionId);
    }

    @Test
    void testGetPrescriptionById_NotFound() {
        // Given
        Long prescriptionId = 999L;
        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        // When
        Optional<Prescription> result = prescriptionService.getPrescriptionById(prescriptionId);

        // Then
        assertFalse(result.isPresent());
        verify(prescriptionRepository).findById(prescriptionId);
    }

    @Test
    void testGetUrgentPrescriptions_Success() {
        // Given
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription2);
        when(prescriptionRepository.findByIsUrgentTrueAndStatusOrderByPrescriptionDateDesc("Active"))
                .thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getUrgentPrescriptions();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription2, result.get(0));
        verify(prescriptionRepository).findByIsUrgentTrueAndStatusOrderByPrescriptionDateDesc("Active");
    }

    @Test
    void testGetPrescriptionsWithUpcomingFollowUp_Success() {
        // Given
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1);
        when(prescriptionRepository.findPrescriptionsWithUpcomingFollowUp(any(LocalDateTime.class)))
                .thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsWithUpcomingFollowUp();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription1, result.get(0));
        verify(prescriptionRepository).findPrescriptionsWithUpcomingFollowUp(any(LocalDateTime.class));
    }

    @Test
    void testCountPrescriptionsByStatus_Success() {
        // Given
        String status = "Active";
        Long expectedCount = 5L;
        when(prescriptionRepository.countByStatus(status)).thenReturn(expectedCount);

        // When
        Long result = prescriptionService.countPrescriptionsByStatus(status);

        // Then
        assertEquals(expectedCount, result);
        verify(prescriptionRepository).countByStatus(status);
    }

    @Test
    void testCountCompletedToday_Success() {
        // Given
        Long expectedCount = 3L;
        when(prescriptionRepository.countCompletedToday(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(expectedCount);

        // When
        Long result = prescriptionService.countCompletedToday();

        // Then
        assertEquals(expectedCount, result);
        verify(prescriptionRepository).countCompletedToday(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testCountPrescriptionsByDoctorAndStatus_Success() {
        // Given
        Long doctorId = 1L;
        String status = "Active";
        Long expectedCount = 2L;
        when(prescriptionRepository.countByDoctorIdAndStatus(doctorId, status)).thenReturn(expectedCount);

        // When
        Long result = prescriptionService.countPrescriptionsByDoctorAndStatus(doctorId, status);

        // Then
        assertEquals(expectedCount, result);
        verify(prescriptionRepository).countByDoctorIdAndStatus(doctorId, status);
    }

    @Test
    void testCountPrescriptionsByPatientAndStatus_Success() {
        // Given
        Long patientId = 1L;
        String status = "Completed";
        Long expectedCount = 1L;
        when(prescriptionRepository.countByPatientIdAndStatus(patientId, status)).thenReturn(expectedCount);

        // When
        Long result = prescriptionService.countPrescriptionsByPatientAndStatus(patientId, status);

        // Then
        assertEquals(expectedCount, result);
        verify(prescriptionRepository).countByPatientIdAndStatus(patientId, status);
    }

    @Test
    void testGetPrescriptionsByDateRange_Success() {
        // Given
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 31, 23, 59);
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1, testPrescription2);
        when(prescriptionRepository.findPrescriptionsByDateRange(start, end)).thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByDateRange(start, end);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testPrescription1));
        assertTrue(result.contains(testPrescription2));
        verify(prescriptionRepository).findPrescriptionsByDateRange(start, end);
    }

    @Test
    void testGetPrescriptionsByPatientId_Success() {
        // Given
        Long patientId = 1L;
        List<Prescription> expectedPrescriptions = Arrays.asList(testPrescription1);
        when(prescriptionRepository.findByPatientIdOrderByPrescriptionDateDesc(patientId))
                .thenReturn(expectedPrescriptions);

        // When
        List<Prescription> result = prescriptionService.getPrescriptionsByPatientId(patientId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPrescription1, result.get(0));
        verify(prescriptionRepository).findByPatientIdOrderByPrescriptionDateDesc(patientId);
    }

    @Test
    void testCreatePrescription_SetsCorrectDefaults() {
        // Given
        Prescription newPrescription = new Prescription();
        newPrescription.setPatientId(1L);
        newPrescription.setDoctorId(1L);
        
        when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> {
            Prescription prescription = invocation.getArgument(0);
            prescription.setPrescriptionId(1L);
            return prescription;
        });

        // When
        Prescription result = prescriptionService.createPrescription(newPrescription);

        // Then
        assertNotNull(result.getPrescriptionDate());
        assertEquals("Active", result.getStatus());
        assertTrue(result.getPrescriptionDate().isBefore(LocalDateTime.now().plusMinutes(1)));
        assertTrue(result.getPrescriptionDate().isAfter(LocalDateTime.now().minusMinutes(1)));
    }
}
