package com.mycompany.capstone.smart_waste_app_backend.repository;

import com.mycompany.capstone.smart_waste_app_backend.model.RouteBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RouteBinRepository extends JpaRepository<RouteBin, Integer> {
    List<RouteBin> findByRoute_RouteIdOrderBySequenceOrder(Integer routeId);
}