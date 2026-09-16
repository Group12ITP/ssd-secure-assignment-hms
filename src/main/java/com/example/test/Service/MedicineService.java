package com.example.test.Service;

import com.example.test.Model.Medicine;
import com.example.test.Repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findByIsActiveTrue();
    }

    public List<Medicine> getMedicinesByCategory(String category) {
        return medicineRepository.findByCategoryAndIsActiveTrue(category);
    }

    public List<String> getAllCategories() {
        return medicineRepository.findAllCategories();
    }

    public List<Medicine> searchMedicines(String category, String medicineName) {
        return medicineRepository.searchMedicines(category, medicineName);
    }

    public List<Medicine> getPrescriptionMedicinesByCategory(String category) {
        return medicineRepository.findPrescriptionMedicinesByCategory(category);
    }

    public Optional<Medicine> getMedicineById(Long medicineId) {
        return medicineRepository.findById(medicineId);
    }

    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId).orElse(null);
        if (medicine != null) {
            medicine.setIsActive(false);
            medicineRepository.save(medicine);
        }
    }

    public List<Medicine> getMedicinesInStock() {
        return medicineRepository.findByStockQuantityGreaterThanAndIsActiveTrue(0);
    }
}


