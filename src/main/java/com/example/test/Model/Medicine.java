package com.example.test.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    @NotBlank(message = "Medicine name is required")
    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @NotBlank(message = "Generic name is required")
    @Column(name = "generic_name", nullable = false)
    private String genericName;

    @NotBlank(message = "Category is required")
    @Column(name = "category", nullable = false)
    private String category; // e.g., "Cardiovascular", "Respiratory", "Gastrointestinal", "Neurological", "Endocrine"

    @NotBlank(message = "Dosage form is required")
    @Column(name = "dosage_form", nullable = false)
    private String dosageForm; // e.g., "Tablet", "Capsule", "Syrup", "Injection", "Cream"

    @NotBlank(message = "Strength is required")
    @Column(name = "strength", nullable = false)
    private String strength; // e.g., "500mg", "10ml", "2.5mg"

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "indications", length = 1000)
    private String indications; // What conditions it treats

    @Column(name = "contraindications", length = 1000)
    private String contraindications; // When not to use

    @Column(name = "side_effects", length = 1000)
    private String sideEffects;

    @Column(name = "dosage_instructions", length = 1000)
    private String dosageInstructions; // General dosage guidelines

    @Column(name = "storage_conditions")
    private String storageConditions;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "is_prescription_required")
    private Boolean isPrescriptionRequired = true;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Constructors
    public Medicine() {}

    public Medicine(String medicineName, String genericName, String category, String dosageForm, String strength) {
        this.medicineName = medicineName;
        this.genericName = genericName;
        this.category = category;
        this.dosageForm = dosageForm;
        this.strength = strength;
    }

    // Getters and Setters
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getDosageInstructions() {
        return dosageInstructions;
    }

    public void setDosageInstructions(String dosageInstructions) {
        this.dosageInstructions = dosageInstructions;
    }

    public String getStorageConditions() {
        return storageConditions;
    }

    public void setStorageConditions(String storageConditions) {
        this.storageConditions = storageConditions;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getIsPrescriptionRequired() {
        return isPrescriptionRequired;
    }

    public void setIsPrescriptionRequired(Boolean isPrescriptionRequired) {
        this.isPrescriptionRequired = isPrescriptionRequired;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
