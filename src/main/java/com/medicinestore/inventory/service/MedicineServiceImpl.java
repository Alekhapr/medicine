package com.medicinestore.inventory.service;

import com.medicinestore.inventory.dto.DashboardStats;
import com.medicinestore.inventory.entity.Medicine;
import com.medicinestore.inventory.exception.ResourceNotFoundException;
import com.medicinestore.inventory.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateMedicine(Long id, Medicine medicine) {
        Medicine existing = getMedicine(id);
        existing.setName(medicine.getName());
        existing.setCompanyName(medicine.getCompanyName());
        existing.setComposition(medicine.getComposition());
        existing.setType(medicine.getType());
        existing.setPrice(medicine.getPrice());
        existing.setStockQuantity(medicine.getStockQuantity());
        existing.setExpiryDate(medicine.getExpiryDate());
        existing.setMedicineClass(medicine.getMedicineClass());
        existing.setShelfLocation(medicine.getShelfLocation());
        existing.setDescription(medicine.getDescription());
        existing.setLowStockThreshold(medicine.getLowStockThreshold());
        return medicineRepository.save(existing);
    }

    @Override
    public Medicine updateStock(Long id, Integer stockQuantity) {
        Medicine medicine = getMedicine(id);
        medicine.setStockQuantity(stockQuantity);
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateExpiryDate(Long id, LocalDate expiryDate) {
        Medicine medicine = getMedicine(id);
        medicine.setExpiryDate(expiryDate);
        return medicineRepository.save(medicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        Medicine medicine = getMedicine(id);
        medicineRepository.delete(medicine);
    }

    @Override
    public Medicine getMedicine(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));
    }

    @Override
    public Page<Medicine> getMedicines(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return medicineRepository.findAll(pageable);
        }
        return medicineRepository.findByNameContainingIgnoreCase(search.trim(), pageable);
    }

    @Override
    public List<Medicine> getExpiredMedicines() {
        return medicineRepository.findExpiredMedicines(LocalDate.now());
    }

    @Override
    public List<Medicine> getExpiringSoonMedicines() {
        LocalDate today = LocalDate.now();
        return medicineRepository.findExpiringSoonMedicines(today, today.plusDays(30));
    }

    @Override
    public List<Medicine> getLowStockMedicines() {
        return medicineRepository.findLowStockMedicines();
    }

    @Override
    public List<Medicine> getOutOfStockMedicines() {
        return medicineRepository.findOutOfStockMedicines();
    }

    @Override
    public DashboardStats getDashboardStats() {
        return DashboardStats.builder()
                .totalMedicines(medicineRepository.count())
                .totalStock(medicineRepository.calculateTotalStock())
                .expiredCount(medicineRepository.countExpired(LocalDate.now()))
                .lowStockCount(medicineRepository.countLowStock())
                .build();
    }
}
