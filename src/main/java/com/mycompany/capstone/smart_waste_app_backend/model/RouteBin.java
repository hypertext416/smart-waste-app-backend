package com.mycompany.capstone.smart_waste_app_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "route_bins")
@Data
public class RouteBin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_bin_id")
    private Integer routeBinId;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "bin_id", nullable = false)
    private Bin bin;

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;
}