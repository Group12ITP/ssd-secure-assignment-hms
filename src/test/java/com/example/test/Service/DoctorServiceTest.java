package com.example.test.Service;

import com.example.test.Model.Doctor;
import com.example.test.Repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor testDoctor;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        testDoctor = new Doctor();
        testDoctor.setDoctorId(1L);
        testDoctor.setUsername("dr_smith");
        testDoctor.setEmail("dr.smith@example.com");
        testDoctor.setPassword("password123");
        testDoctor.setFullName("Dr. John Smith");
        testDoctor.setSpecialization("Cardiology");
        testDoctor.setContactNumber("1234567890");
        testDoctor.setLicenseNumber("LIC123456");
    }

    @Test
    void testRegisterDoctor_Success() {
        // Given
        when(doctorRepository.findByEmail(testDoctor.getEmail())).thenReturn(null);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(testDoctor);

        // When
        Doctor result = doctorService.registerDoctor(testDoctor);

        // Then
        assertNotNull(result);
        assertEquals(testDoctor.getUsername(), result.getUsername());
        assertEquals(testDoctor.getEmail(), result.getEmail());
        assertTrue(passwordEncoder.matches("password123", result.getPassword()));
        verify(doctorRepository).findByEmail(testDoctor.getEmail());
        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void testRegisterDoctor_EmailAlreadyExists() {
        // Given
        when(doctorRepository.findByEmail(testDoctor.getEmail())).thenReturn(testDoctor);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.registerDoctor(testDoctor);
        });

        assertEquals("Email already exists!", exception.getMessage());
        verify(doctorRepository).findByEmail(testDoctor.getEmail());
        verify(doctorRepository, never()).save(any(Doctor.class));
    }

    @Test
    void testLoginDoctor_Success() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testDoctor.setPassword(encodedPassword);
        when(doctorRepository.findByUsername("dr_smith")).thenReturn(testDoctor);

        // When
        Doctor result = doctorService.loginDoctor("dr_smith", "password123");

        // Then
        assertNotNull(result);
        assertEquals(testDoctor.getUsername(), result.getUsername());
        assertEquals(testDoctor.getEmail(), result.getEmail());
        verify(doctorRepository).findByUsername("dr_smith");
    }

    @Test
    void testLoginDoctor_InvalidUsername() {
        // Given
        when(doctorRepository.findByUsername("invalid_user")).thenReturn(null);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.loginDoctor("invalid_user", "password123");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
        verify(doctorRepository).findByUsername("invalid_user");
    }

    @Test
    void testLoginDoctor_InvalidPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("correct_password");
        testDoctor.setPassword(encodedPassword);
        when(doctorRepository.findByUsername("dr_smith")).thenReturn(testDoctor);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.loginDoctor("dr_smith", "wrong_password");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
        verify(doctorRepository).findByUsername("dr_smith");
    }

    @Test
    void testGetDoctorByUsername_Success() {
        // Given
        when(doctorRepository.findByUsername("dr_smith")).thenReturn(testDoctor);

        // When
        Doctor result = doctorService.getDoctorByUsername("dr_smith");

        // Then
        assertNotNull(result);
        assertEquals(testDoctor.getUsername(), result.getUsername());
        assertEquals(testDoctor.getEmail(), result.getEmail());
        verify(doctorRepository).findByUsername("dr_smith");
    }

    @Test
    void testGetDoctorByUsername_NotFound() {
        // Given
        when(doctorRepository.findByUsername("nonexistent_user")).thenReturn(null);

        // When
        Doctor result = doctorService.getDoctorByUsername("nonexistent_user");

        // Then
        assertNull(result);
        verify(doctorRepository).findByUsername("nonexistent_user");
    }

    @Test
    void testRegisterDoctor_PasswordEncryption() {
        // Given
        when(doctorRepository.findByEmail(testDoctor.getEmail())).thenReturn(null);
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> {
            Doctor doctor = invocation.getArgument(0);
            assertTrue(passwordEncoder.matches("password123", doctor.getPassword()));
            return doctor;
        });

        // When
        doctorService.registerDoctor(testDoctor);

        // Then
        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void testRegisterDoctor_WithNullEmail() {
        // Given
        testDoctor.setEmail(null);
        when(doctorRepository.findByEmail(null)).thenReturn(null);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(testDoctor);

        // When
        Doctor result = doctorService.registerDoctor(testDoctor);

        // Then
        assertNotNull(result);
        verify(doctorRepository).findByEmail(null);
        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void testLoginDoctor_WithEmptyPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testDoctor.setPassword(encodedPassword);
        when(doctorRepository.findByUsername("dr_smith")).thenReturn(testDoctor);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.loginDoctor("dr_smith", "");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    @Test
    void testLoginDoctor_WithNullPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testDoctor.setPassword(encodedPassword);
        when(doctorRepository.findByUsername("dr_smith")).thenReturn(testDoctor);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.loginDoctor("dr_smith", null);
        });

        // The BCryptPasswordEncoder throws this when the raw password is null
        assertEquals("rawPassword cannot be null", exception.getMessage());
    }

}
