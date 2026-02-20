package com.medicinestore.inventory.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardStats {
    private long totalMedicines;
    private long totalStock;
    private long expiredCount;
    private long lowStockCount;
}
