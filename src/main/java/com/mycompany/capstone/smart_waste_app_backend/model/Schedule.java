package com.mycompany.capstone.smart_waste_app_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "schedules")
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "route_name", nullable = false)
    private String routeName;

    @Column(name = "area")
    private String area;

    @Column(name = "collection_day", nullable = false)
    private String collectionDay;

    @Column(name = "collection_time", nullable = false)
    private String collectionTime;
}