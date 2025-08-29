package com.mycompany.capstone.smart_waste_app_backend.repository;

import com.mycompany.capstone.smart_waste_app_backend.model.Bin;
import com.mycompany.capstone.smart_waste_app_backend.model.Bin.BinStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BinRepository extends JpaRepository<Bin, String> { // Bin ID is String
    // Custom query method to find bins by status
    List<Bin> findByStatus(BinStatus status);

    // You can add more specific queries based on your needs, e.g., to find bins in an area
    List<Bin> findByArea(String area);
}