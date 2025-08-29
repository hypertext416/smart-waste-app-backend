package com.mycompany.capstone.smart_waste_app_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Data
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Integer complaintId;

    @ManyToOne // Many complaints can be made by one user
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;

    @ManyToOne // Many complaints can be for one bin
    @JoinColumn(name = "bin_id", nullable = false)
    private Bin bin;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ComplaintStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public enum ComplaintStatus {
        Open, Closed
    }
}