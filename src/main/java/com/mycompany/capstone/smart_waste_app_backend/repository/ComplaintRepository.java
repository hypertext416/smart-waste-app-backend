package com.mycompany.capstone.smart_waste_app_backend.repository;

import com.mycompany.capstone.smart_waste_app_backend.model.Complaint;
import com.mycompany.capstone.smart_waste_app_backend.model.Complaint.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> findByStatus(ComplaintStatus status);
}