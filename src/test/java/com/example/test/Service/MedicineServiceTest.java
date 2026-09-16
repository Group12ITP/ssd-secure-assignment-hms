package com.example.test.Service;

import com.example.test.Model.Medicine;
import com.example.test.Repository.MedicineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineService medicineService;

    private Medicine testMedicine1;
    private Medicine testMedicine2;
    private Medicine inactiveMedicine;

    @BeforeEach
    void setUp() {
        testMedicine1 = new Medicine();
        testMedicine1.setMedicineId(1L);
        testMedicine1.setMedicineName("Paracetamol");
        testMedicine1.setCategory("Pain Relief");
        testMedicine1.setDescription("Pain and fever relief");
        testMedicine1.setDosageForm("500mg");
        testMedicine1.setStockQuantity(100);
        testMedicine1.setUnitPrice(25.50);
        testMedicine1.setIsActive(true);
        testMedicine1.setIsPrescriptionRequired(false);

        testMedicine2 = new Medicine();
        testMedicine2.setMedicineId(2L);
        testMedicine2.setMedicineName("Amoxicillin");
        testMedicine2.setCategory("Antibiotic");
        testMedicine2.setDescription("Antibiotic for bacterial infections");
        testMedicine2.setDosageForm("250mg");
        testMedicine2.setStockQuantity(50);
        testMedicine2.setUnitPrice(45.75);
        testMedicine2.setIsActive(true);
        testMedicine2.setIsPrescriptionRequired(true);

        inactiveMedicine = new Medicine();
        inactiveMedicine.setMedicineId(3L);
        inactiveMedicine.setMedicineName("Old Medicine");
        inactiveMedicine.setCategory("Old Category");
        inactiveMedicine.setIsActive(false);
    }

    @Test
    void testGetAllMedicines_Success() {
        // Given
        List<Medicine> expectedMedicines = Arrays.asList(testMedicine1, testMedicine2);
        when(medicineRepository.findByIsActiveTrue()).thenReturn(expectedMedicines);

        // When
        List<Medicine> result = medicineService.getAllMedicines();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testMedicine1));
        assertTrue(result.contains(testMedicine2));
        verify(medicineRepository).findByIsActiveTrue();
    }

    @Test
    void testGetAllMedicines_EmptyList() {
        // Given
        when(medicineRepository.findByIsActiveTrue()).thenReturn(Arrays.asList());

        // When
        List<Medicine> result = medicineService.getAllMedicines();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicineRepository).findByIsActiveTrue();
    }

    @Test
    void testGetMedicinesByCategory_Success() {
        // Given
        String category = "Pain Relief";
        List<Medicine> expectedMedicines = Arrays.asList(testMedicine1);
        when(medicineRepository.findByCategoryAndIsActiveTrue(category)).thenReturn(expectedMedicines);

        // When
        List<Medicine> result = medicineService.getMedicinesByCategory(category);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testMedicine1, result.get(0));
        verify(medicineRepository).findByCategoryAndIsActiveTrue(category);
    }

    @Test
    void testGetMedicinesByCategory_NoResults() {
        // Given
        String category = "Non-existent Category";
        when(medicineRepository.findByCategoryAndIsActiveTrue(category)).thenReturn(Arrays.asList());

        // When
        List<Medicine> result = medicineService.getMedicinesByCategory(category);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicineRepository).findByCategoryAndIsActiveTrue(category);
    }

    @Test
    void testGetAllCategories_Success() {
        // Given
        List<String> expectedCategories = Arrays.asList("Pain Relief", "Antibiotic", "Cardiology");
        when(medicineRepository.findAllCategories()).thenReturn(expectedCategories);

        // When
        List<String> result = medicineService.getAllCategories();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("Pain Relief"));
        assertTrue(result.contains("Antibiotic"));
        assertTrue(result.contains("Cardiology"));
        verify(medicineRepository).findAllCategories();
    }

    @Test
    void testGetAllCategories_EmptyList() {
        // Given
        when(medicineRepository.findAllCategories()).thenReturn(Arrays.asList());

        // When
        List<String> result = medicineService.getAllCategories();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicineRepository).findAllCategories();
    }

    @Test
    void testSearchMedicines_Success() {
        // Given
        String category = "Pain Relief";
        String medicineName = "Paracetamol";
        List<Medicine> expectedMedicines = Arrays.asList(testMedicine1);
        when(medicineRepository.searchMedicines(category, medicineName)).thenReturn(expectedMedicines);

        // When
        List<Medicine> result = medicineService.searchMedicines(category, medicineName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testMedicine1, result.get(0));
        verify(medicineRepository).searchMedicines(category, medicineName);
    }

    @Test
    void testSearchMedicines_NoResults() {
        // Given
        String category = "Non-existent";
        String medicineName = "Non-existent";
        when(medicineRepository.searchMedicines(category, medicineName)).thenReturn(Arrays.asList());

        // When
        List<Medicine> result = medicineService.searchMedicines(category, medicineName);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicineRepository).searchMedicines(category, medicineName);
    }

    @Test
    void testGetPrescriptionMedicinesByCategory_Success() {
        // Given
        String category = "Antibiotic";
        List<Medicine> expectedMedicines = Arrays.asList(testMedicine2);
        when(medicineRepository.findPrescriptionMedicinesByCategory(category)).thenReturn(expectedMedicines);

        // When
        List<Medicine> result = medicineService.getPrescriptionMedicinesByCategory(category);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testMedicine2, result.get(0));
        verify(medicineRepository).findPrescriptionMedicinesByCategory(category);
    }

    @Test
    void testGetMedicineById_Success() {
        // Given
        Long medicineId = 1L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(testMedicine1));

        // When
        Optional<Medicine> result = medicineService.getMedicineById(medicineId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testMedicine1, result.get());
        verify(medicineRepository).findById(medicineId);
    }

    @Test
    void testGetMedicineById_NotFound() {
        // Given
        Long medicineId = 999L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.empty());

        // When
        Optional<Medicine> result = medicineService.getMedicineById(medicineId);

        // Then
        assertFalse(result.isPresent());
        verify(medicineRepository).findById(medicineId);
    }

    @Test
    void testSaveMedicine_Success() {
        // Given
        when(medicineRepository.save(any(Medicine.class))).thenReturn(testMedicine1);

        // When
        Medicine result = medicineService.saveMedicine(testMedicine1);

        // Then
        assertNotNull(result);
        assertEquals(testMedicine1, result);
        verify(medicineRepository).save(testMedicine1);
    }

    @Test
    void testDeleteMedicine_Success() {
        // Given
        Long medicineId = 1L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(testMedicine1));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(testMedicine1);

        // When
        medicineService.deleteMedicine(medicineId);

        // Then
        assertFalse(testMedicine1.getIsActive());
        verify(medicineRepository).findById(medicineId);
        verify(medicineRepository).save(testMedicine1);
    }

    @Test
    void testDeleteMedicine_MedicineNotFound() {
        // Given
        Long medicineId = 999L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.empty());

        // When
        medicineService.deleteMedicine(medicineId);

        // Then
        verify(medicineRepository).findById(medicineId);
        verify(medicineRepository, never()).save(any(Medicine.class));
    }

    @Test
    void testGetMedicinesInStock_Success() {
        // Given
        List<Medicine> expectedMedicines = Arrays.asList(testMedicine1, testMedicine2);
        when(medicineRepository.findByStockQuantityGreaterThanAndIsActiveTrue(0)).thenReturn(expectedMedicines);

        // When
        List<Medicine> result = medicineService.getMedicinesInStock();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(testMedicine1));
        assertTrue(result.contains(testMedicine2));
        verify(medicineRepository).findByStockQuantityGreaterThanAndIsActiveTrue(0);
    }

    @Test
    void testGetMedicinesInStock_EmptyList() {
        // Given
        when(medicineRepository.findByStockQuantityGreaterThanAndIsActiveTrue(0)).thenReturn(Arrays.asList());

        // When
        List<Medicine> result = medicineService.getMedicinesInStock();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicineRepository).findByStockQuantityGreaterThanAndIsActiveTrue(0);
    }

    @Test
    void testSearchMedicines_WithNullCategory() {
        // Given
        String category = null;
        String medicineName = "Paracetamol";
        List<Medicine> expectedMedicines = Arrays.asList(testMedicine1);
        when(medicineRepository.searchMedicines(category, medicineName)).thenReturn(expectedMedicines);

        // When
        List<Medicine> result = medicineService.searchMedicines(category, medicineName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(medicineRepository).searchMedicines(category, medicineName);
    }

    @Test
    void testSearchMedicines_WithNullMedicineName() {
        // Given
        String category = "Pain Relief";
        String medicineName = null;
        List<Medicine> expectedMedicines = Arrays.asList(testMedicine1);
        when(medicineRepository.searchMedicines(category, medicineName)).thenReturn(expectedMedicines);

        // When
        List<Medicine> result = medicineService.searchMedicines(category, medicineName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(medicineRepository).searchMedicines(category, medicineName);
    }

    @Test
    void testGetPrescriptionMedicinesByCategory_WithNullCategory() {
        // Given
        String category = null;
        when(medicineRepository.findPrescriptionMedicinesByCategory(category)).thenReturn(Arrays.asList());

        // When
        List<Medicine> result = medicineService.getPrescriptionMedicinesByCategory(category);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(medicineRepository).findPrescriptionMedicinesByCategory(category);
    }

    @Test
    void testSaveMedicine_WithNullMedicine() {
        // Given
        when(medicineRepository.save(null)).thenReturn(null);

        // When
        Medicine result = medicineService.saveMedicine(null);

        // Then
        assertNull(result);
        verify(medicineRepository).save(null);
    }
}
