package com.example.test.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

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
    private String licenseNumber;

    @NotBlank
    private String specialization;

    private String qualifications;

    private int yearsOfExperience;

    private String department;

    private String workingDays;   // e.g. Mon-Fri
    private String workingHours;  // e.g. 9 AM - 4 PM

    // --- Account Info ---
    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    private String securityQuestion;
    private String securityAnswer;

    // --- Constructors ---
    public Doctor() {}

    // --- Getters & Setters ---
    // (Generate using IDE)


    public Long getDoctorId() {
        return doctorId;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getQualifications() {
        return qualifications;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getDepartment() {
        return department;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public String getWorkingHours() {
        return workingHours;
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

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
