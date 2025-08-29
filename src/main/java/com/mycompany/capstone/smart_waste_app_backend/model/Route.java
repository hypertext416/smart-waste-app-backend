package com.mycompany.capstone.smart_waste_app_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "routes")
@Data
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "route_name", nullable = false)
    private String routeName;

    @Column(name = "generated_at", updatable = false)
    private LocalDateTime generatedAt;

    @ManyToOne
    @JoinColumn(name = "optimized_by") // User who optimized the route
    private User optimizedBy;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // This is a transient field, not mapped to DB, to hold optimized bin IDs for frontend
    @Transient
    private List<String> optimizedPath;
}