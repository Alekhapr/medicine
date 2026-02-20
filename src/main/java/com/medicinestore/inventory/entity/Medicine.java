package com.medicinestore.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Medicine entity represents each stock item in the medicine store.
 */
@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Medicine name is required")
    @Size(max = 120, message = "Medicine name cannot exceed 120 characters")
    @Column(nullable = false, length = 120)
    private String name;

    @NotBlank(message = "Company name is required")
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String companyName;

    @NotBlank(message = "Composition is required")
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String composition;

    @NotBlank(message = "Medicine type is required")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String type;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    @Digits(integer = 8, fraction = 2)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    @Column(nullable = false)
    private Integer stockQuantity;

    @NotNull(message = "Expiry date is required")
    @Column(nullable = false)
    private LocalDate expiryDate;

    @NotBlank(message = "Medicine class is required")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String medicineClass;

    @NotBlank(message = "Shelf location is required")
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String shelfLocation;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @Builder.Default
    @Column(nullable = false)
    private Integer lowStockThreshold = 10;

    /**
     * Utility method to check expiry status each time data is read.
     */
    @Transient
    public boolean isExpired() {
        return expiryDate != null && expiryDate.isBefore(LocalDate.now());
    }

    @Transient
    public boolean isExpiringSoon() {
        if (expiryDate == null || isExpired()) {
            return false;
        }
        return !expiryDate.isAfter(LocalDate.now().plusDays(30));
    }

    @Transient
    public boolean isLowStock() {
        return stockQuantity != null && lowStockThreshold != null && stockQuantity > 0 && stockQuantity <= lowStockThreshold;
    }

    @Transient
    public boolean isOutOfStock() {
        return stockQuantity != null && stockQuantity == 0;
    }
}
