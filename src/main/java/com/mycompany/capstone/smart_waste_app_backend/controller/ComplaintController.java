package com.mycompany.capstone.smart_waste_app_backend.controller;

import com.mycompany.capstone.smart_waste_app_backend.model.Complaint;
import com.mycompany.capstone.smart_waste_app_backend.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // GET /api/complaints - Get all complaints (can be used for analytics/viewing)
    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    // GET /api/complaints/open - Get only open complaints (for worker dashboard)
    @GetMapping("/open")
    public List<Complaint> getOpenComplaints() {
        return complaintService.getOpenComplaints();
    }

    // POST /api/complaints - Report a new complaint
    @PostMapping
    public ResponseEntity<Complaint> reportComplaint(@RequestBody Map<String, String> complaintRequest) {
        try {
            // In a real app, userId would come from authentication context
            Integer userId = Integer.parseInt(complaintRequest.get("userId"));
            String binId = complaintRequest.get("binId");
            String description = complaintRequest.get("description");

            if (binId == null || description == null || userId == null) {
                return ResponseEntity.badRequest().body(null); // Return 400 if essential data is missing
            }

            Complaint newComplaint = complaintService.reportNewComplaint(userId, binId, description);
            return new ResponseEntity<>(newComplaint, HttpStatus.CREATED); // Return 201 Created
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null); // Invalid user ID format
        } catch (IllegalArgumentException e) {
            // Catch custom exceptions from service for not found user/bin
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Or HttpStatus.NOT_FOUND if appropriate
        }
    }
}