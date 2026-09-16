package com.example.test.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @NotNull(message = "Doctor ID is required")
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @NotNull(message = "Patient ID is required")
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @NotNull(message = "Appointment ID is required")
    @Column(name = "appointment_id", nullable = false)
    private Long appointmentId;

    @NotBlank(message = "Diagnosis is required")
    @Column(name = "diagnosis", length = 1000, nullable = false)
    private String diagnosis;

    @Column(name = "symptoms", length = 1000)
    private String symptoms;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "follow_up_date")
    private LocalDateTime followUpDate;

    @Column(name = "prescription_date", nullable = false)
    private LocalDateTime prescriptionDate;

    @Column(name = "status", nullable = false)
    private String status = "Active"; // Active, Completed, Cancelled

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "is_urgent")
    private Boolean isUrgent = false;

    // Medicine details stored as JSON or separate table
    @Column(name = "medicine_details", length = 2000)
    private String medicineDetails; // JSON string containing medicine list with dosages

    // Constructors
    public Prescription() {}

    public Prescription(Long doctorId, Long patientId, Long appointmentId, String diagnosis) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.prescriptionDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(LocalDateTime followUpDate) {
        this.followUpDate = followUpDate;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(String medicineDetails) {
        this.medicineDetails = medicineDetails;
    }
}
