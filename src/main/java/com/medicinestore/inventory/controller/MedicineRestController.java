package com.medicinestore.inventory.controller;

import com.medicinestore.inventory.dto.ApiResponse;
import com.medicinestore.inventory.dto.DashboardStats;
import com.medicinestore.inventory.dto.StockUpdateRequest;
import com.medicinestore.inventory.entity.Medicine;
import com.medicinestore.inventory.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineRestController {

    private final MedicineService medicineService;

    @PostMapping
    public ResponseEntity<Medicine> addMedicine(@Valid @RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineService.addMedicine(medicine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @Valid @RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, medicine));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Medicine> updateStock(@PathVariable Long id, @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(medicineService.updateStock(id, request.getStockQuantity()));
    }

    @PatchMapping("/{id}/expiry")
    public ResponseEntity<Medicine> updateExpiryDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate) {
        return ResponseEntity.ok(medicineService.updateExpiryDate(id, expiryDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok(new ApiResponse("Medicine deleted successfully"));
    }

    @GetMapping
    public ResponseEntity<Page<Medicine>> getMedicines(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(medicineService.getMedicines(search, pageable));
    }

    @GetMapping("/filter/expired")
    public ResponseEntity<List<Medicine>> getExpired() {
        return ResponseEntity.ok(medicineService.getExpiredMedicines());
    }

    @GetMapping("/filter/expiring-soon")
    public ResponseEntity<List<Medicine>> getExpiringSoon() {
        return ResponseEntity.ok(medicineService.getExpiringSoonMedicines());
    }

    @GetMapping("/filter/low-stock")
    public ResponseEntity<List<Medicine>> getLowStock() {
        return ResponseEntity.ok(medicineService.getLowStockMedicines());
    }

    @GetMapping("/filter/out-of-stock")
    public ResponseEntity<List<Medicine>> getOutOfStock() {
        return ResponseEntity.ok(medicineService.getOutOfStockMedicines());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        return ResponseEntity.ok(medicineService.getDashboardStats());
    }
}
