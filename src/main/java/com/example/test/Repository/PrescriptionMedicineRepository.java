package com.example.test.Repository;

import com.example.test.Model.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {

    // Find all medicines for a specific prescription
    List<PrescriptionMedicine> findByPrescriptionId(Long prescriptionId);

    // Find all prescriptions for a specific medicine
    List<PrescriptionMedicine> findByMedicineId(Long medicineId);

    // Find medicines by prescription and medicine ID
    PrescriptionMedicine findByPrescriptionIdAndMedicineId(Long prescriptionId, Long medicineId);

    // Get total count of medicines for a prescription
    Long countByPrescriptionId(Long prescriptionId);

    // Get total price for a prescription
    @Query("SELECT SUM(pm.totalPrice) FROM PrescriptionMedicine pm WHERE pm.prescriptionId = :prescriptionId")
    Double getTotalPriceByPrescriptionId(@Param("prescriptionId") Long prescriptionId);

    // Find all prescription medicines with details
    @Query("SELECT pm FROM PrescriptionMedicine pm WHERE pm.prescriptionId = :prescriptionId ORDER BY pm.medicineName")
    List<PrescriptionMedicine> findPrescriptionMedicinesWithDetails(@Param("prescriptionId") Long prescriptionId);

    // Find prescription medicines by status (through prescription)
    @Query("SELECT pm FROM PrescriptionMedicine pm " +
           "JOIN Prescription p ON pm.prescriptionId = p.prescriptionId " +
           "WHERE p.status = :status ORDER BY p.prescriptionDate DESC")
    List<PrescriptionMedicine> findByPrescriptionStatus(@Param("status") String status);

    // Find prescription medicines for pharmacist orders
    @Query("SELECT pm FROM PrescriptionMedicine pm " +
           "JOIN Prescription p ON pm.prescriptionId = p.prescriptionId " +
           "WHERE p.status = 'Active' ORDER BY p.prescriptionDate DESC")
    List<PrescriptionMedicine> findActivePrescriptionMedicines();
}


