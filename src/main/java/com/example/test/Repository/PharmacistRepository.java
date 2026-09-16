package com.example.test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.test.Model.Pharmacist;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
    Pharmacist findByEmail(String email);
    Pharmacist findByUsername(String username);
}
