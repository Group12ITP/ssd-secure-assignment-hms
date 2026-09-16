package com.example.test.Repository;

import com.example.test.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);
    Patient findByUsername(String username);
    Patient findByNicPassport(String nicPassport);
}