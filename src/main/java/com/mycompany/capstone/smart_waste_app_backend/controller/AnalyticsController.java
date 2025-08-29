package com.mycompany.capstone.smart_waste_app_backend.controller;

import com.mycompany.capstone.smart_waste_app_backend.model.Bin;
import com.mycompany.capstone.smart_waste_app_backend.model.Bin.BinStatus;
import com.mycompany.capstone.smart_waste_app_backend.model.Complaint;
import com.mycompany.capstone.smart_waste_app_backend.model.Complaint.ComplaintStatus;
import com.mycompany.capstone.smart_waste_app_backend.repository.BinRepository;
import com.mycompany.capstone.smart_waste_app_backend.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private BinRepository binRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    // GET /api/analytics/full-bins-by-area
    @GetMapping("/full-bins-by-area")
    public List<Map<String, Object>> getFullBinsByArea() {
        List<Bin> fullBins = binRepository.findByStatus(BinStatus.Full);
        Map<String, Long> counts = fullBins.stream()
                .collect(Collectors.groupingBy(Bin::getArea, Collectors.counting()));

        return counts.entrySet().stream()
                .map(entry -> Map.of("area", (Object) entry.getKey(), "fullBinCount", (Object) entry.getValue()))
                .collect(Collectors.toList());
    }

    // GET /api/analytics/complaints-by-area
    @GetMapping("/complaints-by-area")
    public List<Map<String, Object>> getComplaintFrequencyByArea() {
        List<Complaint> openComplaints = complaintRepository.findByStatus(ComplaintStatus.Open);
        Map<String, Long> counts = openComplaints.stream()
                .filter(c -> c.getBin() != null && c.getBin().getArea() != null)
                .collect(Collectors.groupingBy(c -> c.getBin().getArea(), Collectors.counting()));

        return counts.entrySet().stream()
                .map(entry -> Map.of("area", (Object) entry.getKey(), "complaintCount", (Object) entry.getValue()))
                .collect(Collectors.toList());
    }
}