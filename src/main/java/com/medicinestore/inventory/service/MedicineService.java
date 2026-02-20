package com.medicinestore.inventory.service;

import com.medicinestore.inventory.dto.DashboardStats;
import com.medicinestore.inventory.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MedicineService {
    Medicine addMedicine(Medicine medicine);
    Medicine updateMedicine(Long id, Medicine medicine);
    Medicine updateStock(Long id, Integer stockQuantity);
    Medicine updateExpiryDate(Long id, LocalDate expiryDate);
    void deleteMedicine(Long id);
    Medicine getMedicine(Long id);
    Page<Medicine> getMedicines(String search, Pageable pageable);
    List<Medicine> getExpiredMedicines();
    List<Medicine> getExpiringSoonMedicines();
    List<Medicine> getLowStockMedicines();
    List<Medicine> getOutOfStockMedicines();
    DashboardStats getDashboardStats();
}
