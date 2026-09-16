package com.example.test.Service;

import com.example.test.Model.Patient;
import com.example.test.Repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        testPatient = new Patient();
        testPatient.setPatientId(1L);
        testPatient.setUsername("patient_john");
        testPatient.setEmail("john.doe@example.com");
        testPatient.setPassword("password123");
        testPatient.setFullName("John Doe");
        testPatient.setContactNumber("1234567890");
        testPatient.setNicPassport("123456789V");
        testPatient.setAddress("123 Main St, City");
        testPatient.setAllergies("Penicillin");
        testPatient.setChronicConditions("Diabetes");
        testPatient.setCurrentMedications("Metformin");
        testPatient.setEmergencyContactName("Jane Doe");
        testPatient.setEmergencyContactNumber("0987654321");
    }

    @Test
    void testRegisterPatient_Success() {
        // Given
        when(patientRepository.findByEmail(testPatient.getEmail())).thenReturn(null);
        when(patientRepository.findByUsername(testPatient.getUsername())).thenReturn(null);
        when(patientRepository.findByNicPassport(testPatient.getNicPassport())).thenReturn(null);
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        // When
        Patient result = patientService.registerPatient(testPatient);

        // Then
        assertNotNull(result);
        assertEquals(testPatient.getUsername(), result.getUsername());
        assertEquals(testPatient.getEmail(), result.getEmail());
        assertTrue(passwordEncoder.matches("password123", result.getPassword()));
        verify(patientRepository).findByEmail(testPatient.getEmail());
        verify(patientRepository).findByUsername(testPatient.getUsername());
        verify(patientRepository).findByNicPassport(testPatient.getNicPassport());
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testRegisterPatient_EmailAlreadyExists() {
        // Given
        when(patientRepository.findByEmail(testPatient.getEmail())).thenReturn(testPatient);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.registerPatient(testPatient);
        });

        assertEquals("Email already exists!", exception.getMessage());
        verify(patientRepository).findByEmail(testPatient.getEmail());
        verify(patientRepository, never()).findByUsername(anyString());
        verify(patientRepository, never()).findByNicPassport(anyString());
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void testRegisterPatient_UsernameAlreadyExists() {
        // Given
        when(patientRepository.findByEmail(testPatient.getEmail())).thenReturn(null);
        when(patientRepository.findByUsername(testPatient.getUsername())).thenReturn(testPatient);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.registerPatient(testPatient);
        });

        assertEquals("Username already exists!", exception.getMessage());
        verify(patientRepository).findByEmail(testPatient.getEmail());
        verify(patientRepository).findByUsername(testPatient.getUsername());
        verify(patientRepository, never()).findByNicPassport(anyString());
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void testRegisterPatient_NicPassportAlreadyExists() {
        // Given
        when(patientRepository.findByEmail(testPatient.getEmail())).thenReturn(null);
        when(patientRepository.findByUsername(testPatient.getUsername())).thenReturn(null);
        when(patientRepository.findByNicPassport(testPatient.getNicPassport())).thenReturn(testPatient);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.registerPatient(testPatient);
        });

        assertEquals("NIC/Passport number already exists!", exception.getMessage());
        verify(patientRepository).findByEmail(testPatient.getEmail());
        verify(patientRepository).findByUsername(testPatient.getUsername());
        verify(patientRepository).findByNicPassport(testPatient.getNicPassport());
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void testLoginPatient_Success() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testPatient.setPassword(encodedPassword);
        when(patientRepository.findByUsername("patient_john")).thenReturn(testPatient);

        // When
        Patient result = patientService.loginPatient("patient_john", "password123");

        // Then
        assertNotNull(result);
        assertEquals(testPatient.getUsername(), result.getUsername());
        assertEquals(testPatient.getEmail(), result.getEmail());
        verify(patientRepository).findByUsername("patient_john");
    }

    @Test
    void testLoginPatient_InvalidUsername() {
        // Given
        when(patientRepository.findByUsername("invalid_user")).thenReturn(null);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.loginPatient("invalid_user", "password123");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
        verify(patientRepository).findByUsername("invalid_user");
    }

    @Test
    void testLoginPatient_InvalidPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("correct_password");
        testPatient.setPassword(encodedPassword);
        when(patientRepository.findByUsername("patient_john")).thenReturn(testPatient);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.loginPatient("patient_john", "wrong_password");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
        verify(patientRepository).findByUsername("patient_john");
    }

    @Test
    void testUpdatePatient_Success() {
        // Given
        Long patientId = 1L;
        Patient patientDetails = new Patient();
        patientDetails.setFullName("John Updated");
        patientDetails.setContactNumber("9876543210");
        patientDetails.setAddress("456 New St, City");
        patientDetails.setAllergies("Aspirin");
        patientDetails.setChronicConditions("Hypertension");
        patientDetails.setCurrentMedications("Lisinopril");
        patientDetails.setEmergencyContactName("Bob Smith");
        patientDetails.setEmergencyContactNumber("1122334455");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        // When
        Patient result = patientService.updatePatient(patientId, patientDetails);

        // Then
        assertNotNull(result);
        assertEquals("John Updated", result.getFullName());
        assertEquals("9876543210", result.getContactNumber());
        assertEquals("456 New St, City", result.getAddress());
        assertEquals("Aspirin", result.getAllergies());
        assertEquals("Hypertension", result.getChronicConditions());
        assertEquals("Lisinopril", result.getCurrentMedications());
        assertEquals("Bob Smith", result.getEmergencyContactName());
        assertEquals("1122334455", result.getEmergencyContactNumber());
        verify(patientRepository).findById(patientId);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testUpdatePatient_PatientNotFound() {
        // Given
        Long patientId = 999L;
        Patient patientDetails = new Patient();
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.updatePatient(patientId, patientDetails);
        });

        assertEquals("Patient not found with id: 999", exception.getMessage());
        verify(patientRepository).findById(patientId);
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void testUpdatePatient_WithNullFields() {
        // Given
        Long patientId = 1L;
        Patient patientDetails = new Patient();
        patientDetails.setFullName(null);
        patientDetails.setContactNumber(null);
        patientDetails.setAddress(null);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        // When
        Patient result = patientService.updatePatient(patientId, patientDetails);

        // Then
        assertNotNull(result);
        // Original values should remain unchanged
        assertEquals("John Doe", result.getFullName());
        assertEquals("1234567890", result.getContactNumber());
        assertEquals("123 Main St, City", result.getAddress());
        verify(patientRepository).findById(patientId);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testGetPatientById_Success() {
        // Given
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));

        // When
        Patient result = patientService.getPatientById(patientId);

        // Then
        assertNotNull(result);
        assertEquals(testPatient.getPatientId(), result.getPatientId());
        assertEquals(testPatient.getUsername(), result.getUsername());
        verify(patientRepository).findById(patientId);
    }

    @Test
    void testGetPatientById_NotFound() {
        // Given
        Long patientId = 999L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.getPatientById(patientId);
        });

        assertEquals("Patient not found with id: 999", exception.getMessage());
        verify(patientRepository).findById(patientId);
    }

    @Test
    void testRegisterPatient_PasswordEncryption() {
        // Given
        when(patientRepository.findByEmail(testPatient.getEmail())).thenReturn(null);
        when(patientRepository.findByUsername(testPatient.getUsername())).thenReturn(null);
        when(patientRepository.findByNicPassport(testPatient.getNicPassport())).thenReturn(null);
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> {
            Patient patient = invocation.getArgument(0);
            assertTrue(passwordEncoder.matches("password123", patient.getPassword()));
            return patient;
        });

        // When
        patientService.registerPatient(testPatient);

        // Then
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testLoginPatient_WithEmptyPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testPatient.setPassword(encodedPassword);
        when(patientRepository.findByUsername("patient_john")).thenReturn(testPatient);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.loginPatient("patient_john", "");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    @Test
    void testLoginPatient_WithNullPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testPatient.setPassword(encodedPassword);
        when(patientRepository.findByUsername("patient_john")).thenReturn(testPatient);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.loginPatient("patient_john", null);
        });

        assertEquals("rawPassword cannot be null", exception.getMessage());
    }
}
