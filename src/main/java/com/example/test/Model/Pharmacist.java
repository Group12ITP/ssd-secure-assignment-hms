package com.example.test.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pharmacists")
public class Pharmacist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pharmacist_id")  // optional, Hibernate will auto-name it anyway
    private Long pharmacistId;


    // --- Personal Information ---
    @NotBlank
    private String fullName;

    @NotBlank
    @Column(unique = true)
    private String nic;

    @NotBlank
    private String gender;

    @NotNull
    private String dateOfBirth;

    @NotBlank
    private String contactNumber;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    private String address;

    // --- Professional Information ---
    @NotBlank
    private String registrationNumber;

    private String qualification;
    private int yearsOfExperience;
    private String department;
    private String dutyHours;

    // --- Account Information ---
    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    // Optional for password recovery
    private String securityQuestion;
    private String securityAnswer;

    // --- Constructors ---
    public Pharmacist() {}

    public Long getPharmacistId() {
        return pharmacistId;
    }
// --- Getters & Setters ---

    public void setPharmacistId(Long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setDutyHours(String dutyHours) {
        this.dutyHours = dutyHours;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNic() {
        return nic;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getDepartment() {
        return department;
    }

    public String getDutyHours() {
        return dutyHours;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
    // (Generate using your IDE)
}
