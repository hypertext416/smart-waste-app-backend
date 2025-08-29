package com.mycompany.capstone.smart_waste_app_backend.service;

import com.mycompany.capstone.smart_waste_app_backend.model.Bin;
import com.mycompany.capstone.smart_waste_app_backend.model.Complaint;
import com.mycompany.capstone.smart_waste_app_backend.model.Complaint.ComplaintStatus;
import com.mycompany.capstone.smart_waste_app_backend.model.User;
import com.mycompany.capstone.smart_waste_app_backend.repository.BinRepository;
import com.mycompany.capstone.smart_waste_app_backend.repository.ComplaintRepository;
import com.mycompany.capstone.smart_waste_app_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BinRepository binRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getOpenComplaints() {
        return complaintRepository.findByStatus(ComplaintStatus.Open);
    }

    // Corresponds to your ConsoleApp's reportOverflowingBin
    public Complaint reportNewComplaint(Integer userId, String binId, String description) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Bin> binOptional = binRepository.findById(binId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found.");
        }
        if (binOptional.isEmpty()) {
            throw new IllegalArgumentException("Bin with ID " + binId + " not found.");
        }

        Complaint complaint = new Complaint();
        complaint.setUser(userOptional.get());
        complaint.setBin(binOptional.get());
        complaint.setDescription(description);
        complaint.setStatus(ComplaintStatus.Open); // New complaints are always 'Open'
        complaint.setCreatedAt(LocalDateTime.now());

        return complaintRepository.save(complaint); // Save and return the new complaint
    }
}