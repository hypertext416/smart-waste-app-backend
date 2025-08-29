package com.mycompany.capstone.smart_waste_app_backend.repository;

import com.mycompany.capstone.smart_waste_app_backend.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
}