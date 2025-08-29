// src/main/java/com/mycompany/capstone/smart_waste_app_backend/model/Bin.java
package com.mycompany.capstone.smart_waste_app_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal; // Import BigDecimal
import java.time.LocalDateTime;

@Entity
@Table(name = "bins")
@Data
public class Bin {

    @Id
    @Column(name = "bin_id")
    private String binId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "area")
    private String area;

    // CHANGE: Use BigDecimal for latitude and longitude
    @Column(name = "latitude", precision = 10, scale = 8) // Precision and scale are valid for BigDecimal
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8) // Precision and scale are valid for BigDecimal
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BinStatus status;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public enum BinStatus {
        Empty, Half, Full
    }
}