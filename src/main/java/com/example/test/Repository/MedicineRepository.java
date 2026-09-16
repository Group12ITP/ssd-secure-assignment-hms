package com.example.test.Repository;

import com.example.test.Model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Find medicines by category
    List<Medicine> findByCategory(String category);

    // Find medicines by name (case insensitive)
    List<Medicine> findByMedicineNameContainingIgnoreCase(String medicineName);

    // Find active medicines
    List<Medicine> findByIsActiveTrue();

    // Find medicines by category and active status
    List<Medicine> findByCategoryAndIsActiveTrue(String category);

    // Find medicines that require prescription
    List<Medicine> findByIsPrescriptionRequiredTrueAndIsActiveTrue();

    // Find medicines by category that require prescription
    @Query("SELECT m FROM Medicine m WHERE m.category = :category AND m.isPrescriptionRequired = true AND m.isActive = true")
    List<Medicine> findPrescriptionMedicinesByCategory(@Param("category") String category);

    // Find medicines with stock
    List<Medicine> findByStockQuantityGreaterThanAndIsActiveTrue(Integer stockQuantity);

    // Search medicines by multiple criteria
    @Query("SELECT m FROM Medicine m WHERE " +
           "(:category IS NULL OR m.category = :category) AND " +
           "(:medicineName IS NULL OR LOWER(m.medicineName) LIKE LOWER(CONCAT('%', :medicineName, '%'))) AND " +
           "m.isActive = true")
    List<Medicine> searchMedicines(@Param("category") String category, 
                                   @Param("medicineName") String medicineName);

    // Get all unique categories
    @Query("SELECT DISTINCT m.category FROM Medicine m WHERE m.isActive = true ORDER BY m.category")
    List<String> findAllCategories();
}


