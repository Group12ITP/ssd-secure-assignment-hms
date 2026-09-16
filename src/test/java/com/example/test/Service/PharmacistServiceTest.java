package com.example.test.Service;

import com.example.test.Model.Pharmacist;
import com.example.test.Repository.PharmacistRepository;
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
class PharmacistServiceTest {

    @Mock
    private PharmacistRepository pharmacistRepository;

    @InjectMocks
    private PharmacistService pharmacistService;

    private Pharmacist testPharmacist;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        testPharmacist = new Pharmacist();
        testPharmacist.setPharmacistId(1L);
        testPharmacist.setUsername("pharmacist_jane");
        testPharmacist.setEmail("jane.pharmacist@example.com");
        testPharmacist.setPassword("password123");
        testPharmacist.setFullName("Jane Pharmacist");
        testPharmacist.setContactNumber("1234567890");
        testPharmacist.setRegistrationNumber("PHARM123456");
        testPharmacist.setFullName("City Pharmacy");
        testPharmacist.setAddress("123 Pharmacy St, City");
    }

    @Test
    void testRegisterPharmacist_Success() {
        // Given
        when(pharmacistRepository.findByEmail(testPharmacist.getEmail())).thenReturn(null);
        when(pharmacistRepository.save(any(Pharmacist.class))).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.registerPharmacist(testPharmacist);

        // Then
        assertNotNull(result);
        assertEquals(testPharmacist.getUsername(), result.getUsername());
        assertEquals(testPharmacist.getEmail(), result.getEmail());
        assertTrue(passwordEncoder.matches("password123", result.getPassword()));
        verify(pharmacistRepository).findByEmail(testPharmacist.getEmail());
        verify(pharmacistRepository).save(any(Pharmacist.class));
    }

    @Test
    void testRegisterPharmacist_EmailAlreadyExists() {
        // Given
        when(pharmacistRepository.findByEmail(testPharmacist.getEmail())).thenReturn(testPharmacist);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pharmacistService.registerPharmacist(testPharmacist);
        });

        assertEquals("Email already exists!", exception.getMessage());
        verify(pharmacistRepository).findByEmail(testPharmacist.getEmail());
        verify(pharmacistRepository, never()).save(any(Pharmacist.class));
    }

    @Test
    void testRegisterPharmacist_WithNullEmail() {
        // Given
        testPharmacist.setEmail(null);
        when(pharmacistRepository.findByEmail(null)).thenReturn(null);
        when(pharmacistRepository.save(any(Pharmacist.class))).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.registerPharmacist(testPharmacist);

        // Then
        assertNotNull(result);
        verify(pharmacistRepository).findByEmail(null);
        verify(pharmacistRepository).save(any(Pharmacist.class));
    }

    @Test
    void testRegisterPharmacist_PasswordEncryption() {
        // Given
        when(pharmacistRepository.findByEmail(testPharmacist.getEmail())).thenReturn(null);
        when(pharmacistRepository.save(any(Pharmacist.class))).thenAnswer(invocation -> {
            Pharmacist pharmacist = invocation.getArgument(0);
            assertTrue(passwordEncoder.matches("password123", pharmacist.getPassword()));
            return pharmacist;
        });

        // When
        pharmacistService.registerPharmacist(testPharmacist);

        // Then
        verify(pharmacistRepository).save(any(Pharmacist.class));
    }

    @Test
    void testLoginPharmacist_Success() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testPharmacist.setPassword(encodedPassword);
        when(pharmacistRepository.findByUsername("pharmacist_jane")).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.loginPharmacist("pharmacist_jane", "password123");

        // Then
        assertNotNull(result);
        assertEquals(testPharmacist.getUsername(), result.getUsername());
        assertEquals(testPharmacist.getEmail(), result.getEmail());
        verify(pharmacistRepository).findByUsername("pharmacist_jane");
    }

    @Test
    void testLoginPharmacist_InvalidUsername() {
        // Given
        when(pharmacistRepository.findByUsername("invalid_user")).thenReturn(null);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pharmacistService.loginPharmacist("invalid_user", "password123");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
        verify(pharmacistRepository).findByUsername("invalid_user");
    }

    @Test
    void testLoginPharmacist_InvalidPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("correct_password");
        testPharmacist.setPassword(encodedPassword);
        when(pharmacistRepository.findByUsername("pharmacist_jane")).thenReturn(testPharmacist);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pharmacistService.loginPharmacist("pharmacist_jane", "wrong_password");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
        verify(pharmacistRepository).findByUsername("pharmacist_jane");
    }

    @Test
    void testLoginPharmacist_WithEmptyPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testPharmacist.setPassword(encodedPassword);
        when(pharmacistRepository.findByUsername("pharmacist_jane")).thenReturn(testPharmacist);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pharmacistService.loginPharmacist("pharmacist_jane", "");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
    }

    @Test
    void testLoginPharmacist_WithNullPassword() {
        // Given
        String encodedPassword = passwordEncoder.encode("password123");
        testPharmacist.setPassword(encodedPassword);
        when(pharmacistRepository.findByUsername("pharmacist_jane")).thenReturn(testPharmacist);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pharmacistService.loginPharmacist("pharmacist_jane", null);
        });

        // BCryptPasswordEncoder throws this message when rawPassword is null
        assertEquals("rawPassword cannot be null", exception.getMessage());
    }


    @Test
    void testGetPharmacistByUsername_Success() {
        // Given
        when(pharmacistRepository.findByUsername("pharmacist_jane")).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.getPharmacistByUsername("pharmacist_jane");

        // Then
        assertNotNull(result);
        assertEquals(testPharmacist.getUsername(), result.getUsername());
        assertEquals(testPharmacist.getEmail(), result.getEmail());
        assertEquals(testPharmacist.getFullName(), result.getFullName());
        assertEquals(testPharmacist.getContactNumber(), result.getContactNumber());
        assertEquals(testPharmacist.getRegistrationNumber(), result.getRegistrationNumber());
        assertEquals(testPharmacist.getFullName(), result.getFullName());
        assertEquals(testPharmacist.getAddress(), result.getAddress());
        verify(pharmacistRepository).findByUsername("pharmacist_jane");
    }

    @Test
    void testGetPharmacistByUsername_NotFound() {
        // Given
        when(pharmacistRepository.findByUsername("nonexistent_user")).thenReturn(null);

        // When
        Pharmacist result = pharmacistService.getPharmacistByUsername("nonexistent_user");

        // Then
        assertNull(result);
        verify(pharmacistRepository).findByUsername("nonexistent_user");
    }

    @Test
    void testGetPharmacistByUsername_WithNullUsername() {
        // Given
        when(pharmacistRepository.findByUsername(null)).thenReturn(null);

        // When
        Pharmacist result = pharmacistService.getPharmacistByUsername(null);

        // Then
        assertNull(result);
        verify(pharmacistRepository).findByUsername(null);
    }

    @Test
    void testGetPharmacistByUsername_WithEmptyUsername() {
        // Given
        when(pharmacistRepository.findByUsername("")).thenReturn(null);

        // When
        Pharmacist result = pharmacistService.getPharmacistByUsername("");

        // Then
        assertNull(result);
        verify(pharmacistRepository).findByUsername("");
    }

    @Test
    void testRegisterPharmacist_WithEmptyPassword() {
        // Given
        testPharmacist.setPassword("");
        when(pharmacistRepository.findByEmail(testPharmacist.getEmail())).thenReturn(null);
        when(pharmacistRepository.save(any(Pharmacist.class))).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.registerPharmacist(testPharmacist);

        // Then
        assertNotNull(result);
        assertTrue(passwordEncoder.matches("", result.getPassword()));
        verify(pharmacistRepository).save(any(Pharmacist.class));
    }


    @Test
    void testLoginPharmacist_WithSpecialCharactersInUsername() {
        // Given
        String specialUsername = "pharmacist@#$%";
        when(pharmacistRepository.findByUsername(specialUsername)).thenReturn(null);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pharmacistService.loginPharmacist(specialUsername, "password123");
        });

        assertEquals("Invalid username or password.", exception.getMessage());
        verify(pharmacistRepository).findByUsername(specialUsername);
    }

    @Test
    void testRegisterPharmacist_WithSpecialCharactersInEmail() {
        // Given
        testPharmacist.setEmail("test+tag@example.com");
        when(pharmacistRepository.findByEmail(testPharmacist.getEmail())).thenReturn(null);
        when(pharmacistRepository.save(any(Pharmacist.class))).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.registerPharmacist(testPharmacist);

        // Then
        assertNotNull(result);
        assertEquals("test+tag@example.com", result.getEmail());
        verify(pharmacistRepository).findByEmail(testPharmacist.getEmail());
        verify(pharmacistRepository).save(any(Pharmacist.class));
    }

    @Test
    void testRegisterPharmacist_WithLongPassword() {
        // Given
        String longPassword = "a".repeat(72); // Use 72 characters to stay within BCrypt limit
        testPharmacist.setPassword(longPassword);
        when(pharmacistRepository.findByEmail(testPharmacist.getEmail())).thenReturn(null);
        when(pharmacistRepository.save(any(Pharmacist.class))).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.registerPharmacist(testPharmacist);

        // Then
        assertNotNull(result);
        assertTrue(passwordEncoder.matches(longPassword, result.getPassword()));
        verify(pharmacistRepository).save(any(Pharmacist.class));
    }

    @Test
    void testLoginPharmacist_WithLongPassword() {
        // Given
        String longPassword = "a".repeat(72); // Use 72 characters to stay within BCrypt limit
        String encodedPassword = passwordEncoder.encode(longPassword);
        testPharmacist.setPassword(encodedPassword);
        when(pharmacistRepository.findByUsername("pharmacist_jane")).thenReturn(testPharmacist);

        // When
        Pharmacist result = pharmacistService.loginPharmacist("pharmacist_jane", longPassword);

        // Then
        assertNotNull(result);
        assertEquals(testPharmacist.getUsername(), result.getUsername());
        verify(pharmacistRepository).findByUsername("pharmacist_jane");
    }
}
