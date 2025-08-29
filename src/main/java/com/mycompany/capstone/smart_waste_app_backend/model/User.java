package com.mycompany.capstone.smart_waste_app_backend.model;

import jakarta.persistence.*;
import lombok.Data; // From Lombok dependency
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Matches your MySQL table name
@Data // Lombok annotation to generate getters, setters, toString, equals, hashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; // In production, store hashed passwords!

    @Enumerated(EnumType.STRING) // Store enum as String in DB
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Enum for roles
    public enum Role {
        citizen, worker, admin
    }
}