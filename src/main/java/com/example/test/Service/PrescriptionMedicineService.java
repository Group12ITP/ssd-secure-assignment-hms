package com.example.test.Service;

import com.example.test.Model.PrescriptionMedicine;
import com.example.test.Model.Medicine;
import com.example.test.Repository.PrescriptionMedicineRepository;
import com.example.test.Repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionMedicineService {

    @Autowired
    private PrescriptionMedicineRepository prescriptionMedicineRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    public PrescriptionMedicine savePrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
        return prescriptionMedicineRepository.save(prescriptionMedicine);
    }

    public List<PrescriptionMedicine> getMedicinesByPrescription(Long prescriptionId) {
        return prescriptionMedicineRepository.findByPrescriptionId(prescriptionId);
    }

    public List<PrescriptionMedicine> getMedicinesByMedicine(Long medicineId) {
        return prescriptionMedicineRepository.findByMedicineId(medicineId);
    }

    public PrescriptionMedicine getPrescriptionMedicine(Long prescriptionId, Long medicineId) {
        return prescriptionMedicineRepository.findByPrescriptionIdAndMedicineId(prescriptionId, medicineId);
    }

    public Long countMedicinesByPrescription(Long prescriptionId) {
        return prescriptionMedicineRepository.countByPrescriptionId(prescriptionId);
    }

    public Double getTotalPriceByPrescription(Long prescriptionId) {
        return prescriptionMedicineRepository.getTotalPriceByPrescriptionId(prescriptionId);
    }

    public List<PrescriptionMedicine> getPrescriptionMedicinesWithDetails(Long prescriptionId) {
        return prescriptionMedicineRepository.findPrescriptionMedicinesWithDetails(prescriptionId);
    }

    public List<PrescriptionMedicine> getActivePrescriptionMedicines() {
        return prescriptionMedicineRepository.findActivePrescriptionMedicines();
    }

    public PrescriptionMedicine createPrescriptionMedicine(Long prescriptionId, Long medicineId, 
                                                          String dosage, String frequency, 
                                                          String duration, String instructions) {
        try {
            System.out.println("=== PRESCRIPTION MEDICINE SERVICE DEBUG ===");
            System.out.println("Creating prescription medicine - Prescription ID: " + prescriptionId + ", Medicine ID: " + medicineId);
            
            // Get medicine details
            Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);
            if (!medicineOpt.isPresent()) {
                throw new RuntimeException("Medicine not found with ID: " + medicineId);
            }
            
            Medicine medicine = medicineOpt.get();
            System.out.println("Found medicine: " + medicine.getMedicineName());
            
            // Create prescription medicine
            PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
            prescriptionMedicine.setPrescriptionId(prescriptionId);
            prescriptionMedicine.setMedicineId(medicineId);
            prescriptionMedicine.setMedicineName(medicine.getMedicineName());
            prescriptionMedicine.setGenericName(medicine.getGenericName());
            prescriptionMedicine.setDosageForm(medicine.getDosageForm());
            prescriptionMedicine.setStrength(medicine.getStrength());
            prescriptionMedicine.setDosage(dosage != null ? dosage : "1 tablet");
            prescriptionMedicine.setFrequency(frequency != null ? frequency : "twice daily");
            prescriptionMedicine.setDuration(duration != null ? duration : "7 days");
            prescriptionMedicine.setInstructions(instructions != null ? instructions : "");
            prescriptionMedicine.setUnitPrice(medicine.getUnitPrice() != null ? medicine.getUnitPrice() : 0.0);
            
            // Calculate quantity based on duration and frequency
            Integer quantity = calculateQuantity(frequency, duration);
            prescriptionMedicine.setQuantity(quantity);
            
            // Calculate total price
            Double totalPrice = quantity * (medicine.getUnitPrice() != null ? medicine.getUnitPrice() : 0.0);
            prescriptionMedicine.setTotalPrice(totalPrice);
            
            System.out.println("Prescription medicine before save: " + prescriptionMedicine);
            PrescriptionMedicine saved = prescriptionMedicineRepository.save(prescriptionMedicine);
            System.out.println("Prescription medicine after save: " + saved);
            System.out.println("Saved prescription medicine ID: " + saved.getPrescriptionMedicineId());
            
            return saved;
        } catch (Exception e) {
            System.err.println("Error creating prescription medicine: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create prescription medicine: " + e.getMessage());
        }
    }

    private Integer calculateQuantity(String frequency, String duration) {
        try {
            // Simple calculation - can be enhanced based on requirements
            int dailyDoses = 1;
            if (frequency != null) {
                if (frequency.toLowerCase().contains("twice") || frequency.toLowerCase().contains("2")) {
                    dailyDoses = 2;
                } else if (frequency.toLowerCase().contains("three") || frequency.toLowerCase().contains("3")) {
                    dailyDoses = 3;
                } else if (frequency.toLowerCase().contains("four") || frequency.toLowerCase().contains("4")) {
                    dailyDoses = 4;
                }
            }
            
            int days = 7; // default
            if (duration != null) {
                if (duration.toLowerCase().contains("week")) {
                    days = 7;
                } else if (duration.toLowerCase().contains("day")) {
                    String[] parts = duration.split("\\s+");
                    for (String part : parts) {
                        try {
                            days = Integer.parseInt(part);
                            break;
                        } catch (NumberFormatException e) {
                            // continue
                        }
                    }
                }
            }
            
            return dailyDoses * days;
        } catch (Exception e) {
            // Return default quantity if calculation fails
            return 7;
        }
    }

    public void deletePrescriptionMedicine(Long prescriptionMedicineId) {
        prescriptionMedicineRepository.deleteById(prescriptionMedicineId);
    }

    public void deleteMedicinesByPrescription(Long prescriptionId) {
        List<PrescriptionMedicine> medicines = prescriptionMedicineRepository.findByPrescriptionId(prescriptionId);
        prescriptionMedicineRepository.deleteAll(medicines);
    }
}
