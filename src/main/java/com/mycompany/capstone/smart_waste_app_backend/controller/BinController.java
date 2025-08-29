package com.mycompany.capstone.smart_waste_app_backend.controller;

import com.mycompany.capstone.smart_waste_app_backend.model.Bin;
import com.mycompany.capstone.smart_waste_app_backend.model.Bin.BinStatus;
import com.mycompany.capstone.smart_waste_app_backend.service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/bins") // Base URL path for all endpoints in this controller
public class BinController {

    @Autowired
    private BinService binService;

    // GET /api/bins - Get all bins
    @GetMapping
    public List<Bin> getAllBins() {
        return binService.getAllBins();
    }

    // GET /api/bins/{id} - Get a single bin by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bin> getBinById(@PathVariable String id) {
        return binService.getBinById(id)
                .map(ResponseEntity::ok) // If bin found, return 200 OK with bin
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }

    // PUT /api/bins/{id}/status - Update bin status (Simulated Monitoring)
    @PutMapping("/{id}/status")
    public ResponseEntity<Bin> updateBinStatus(@PathVariable String id, @RequestBody Map<String, String> payload) {
        String statusString = payload.get("status");
        if (statusString == null || statusString.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request
        }
        try {
            BinStatus newStatus = BinStatus.valueOf(statusString); // Convert string to enum
            Bin updatedBin = binService.updateBinStatus(id, newStatus);
            if (updatedBin != null) {
                return ResponseEntity.ok(updatedBin); // Return 200 OK with updated bin
            } else {
                return ResponseEntity.notFound().build(); // Return 404 Not Found if bin ID invalid
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request for invalid status enum
        }
    }

    // This method could be triggered by an admin from the frontend to simulate a daily increase
    @PostMapping("/simulate-fill-increase")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Return 204 No Content for successful operation
    public void simulateDailyFillIncrease() {
        binService.simulateDailyFillIncrease();
    }
}