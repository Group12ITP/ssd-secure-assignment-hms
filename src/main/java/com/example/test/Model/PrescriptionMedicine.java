package com.example.test.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "prescription_medicines")
public class PrescriptionMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionMedicineId;

    @NotNull(message = "Prescription ID is required")
    @Column(name = "prescription_id", nullable = false)
    private Long prescriptionId;

    @NotNull(message = "Medicine ID is required")
    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @NotBlank(message = "Medicine name is required")
    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @NotBlank(message = "Generic name is required")
    @Column(name = "generic_name", nullable = false)
    private String genericName;

    @NotBlank(message = "Dosage form is required")
    @Column(name = "dosage_form", nullable = false)
    private String dosageForm;

    @NotBlank(message = "Strength is required")
    @Column(name = "strength", nullable = false)
    private String strength;

    @NotBlank(message = "Dosage is required")
    @Column(name = "dosage", nullable = false)
    private String dosage; // e.g., "1 tablet", "2 capsules"

    @NotBlank(message = "Frequency is required")
    @Column(name = "frequency", nullable = false)
    private String frequency; // e.g., "3 times daily", "twice daily"

    @NotBlank(message = "Duration is required")
    @Column(name = "duration", nullable = false)
    private String duration; // e.g., "7 days", "2 weeks"

    @Column(name = "instructions", length = 500)
    private String instructions; // Special instructions

    @Column(name = "quantity")
    private Integer quantity; // Total quantity to dispense

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    // Constructors
    public PrescriptionMedicine() {}

    public PrescriptionMedicine(Long prescriptionId, Long medicineId, String medicineName, 
                               String genericName, String dosageForm, String strength,
                               String dosage, String frequency, String duration) {
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.genericName = genericName;
        this.dosageForm = dosageForm;
        this.strength = strength;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
    }

    // Getters and Setters
    public Long getPrescriptionMedicineId() {
        return prescriptionMedicineId;
    }

    public void setPrescriptionMedicineId(Long prescriptionMedicineId) {
        this.prescriptionMedicineId = prescriptionMedicineId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}


