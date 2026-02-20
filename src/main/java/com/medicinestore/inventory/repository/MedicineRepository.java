package com.medicinestore.inventory.repository;

import com.medicinestore.inventory.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Page<Medicine> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT m FROM Medicine m WHERE m.expiryDate < :today")
    List<Medicine> findExpiredMedicines(@Param("today") LocalDate today);

    @Query("SELECT m FROM Medicine m WHERE m.expiryDate BETWEEN :today AND :limitDate")
    List<Medicine> findExpiringSoonMedicines(@Param("today") LocalDate today, @Param("limitDate") LocalDate limitDate);

    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity > 0 AND m.stockQuantity <= m.lowStockThreshold")
    List<Medicine> findLowStockMedicines();

    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity = 0")
    List<Medicine> findOutOfStockMedicines();

    @Query("SELECT COALESCE(SUM(m.stockQuantity),0) FROM Medicine m")
    Long calculateTotalStock();

    @Query("SELECT COUNT(m) FROM Medicine m WHERE m.expiryDate < :today")
    long countExpired(@Param("today") LocalDate today);

    @Query("SELECT COUNT(m) FROM Medicine m WHERE m.stockQuantity > 0 AND m.stockQuantity <= m.lowStockThreshold")
    long countLowStock();
}
