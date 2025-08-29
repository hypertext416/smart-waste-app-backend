package com.mycompany.capstone.smart_waste_app_backend.service;

import com.mycompany.capstone.smart_waste_app_backend.model.Bin;
import com.mycompany.capstone.smart_waste_app_backend.model.Bin.BinStatus;
import com.mycompany.capstone.smart_waste_app_backend.repository.BinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BinService {

    @Autowired // Spring will automatically inject BinRepository here
    private BinRepository binRepository;

    public List<Bin> getAllBins() {
        return binRepository.findAll();
    }

    public Optional<Bin> getBinById(String binId) {
        return binRepository.findById(binId);
    }

    // Corresponds to your ConsoleApp's updateBinFillLevel
    public Bin updateBinStatus(String binId, BinStatus newStatus) {
        return binRepository.findById(binId).map(bin -> {
            bin.setStatus(newStatus);
            bin.setLastUpdated(LocalDateTime.now());
            return binRepository.save(bin); // Save and return the updated bin
        }).orElse(null); // Return null if bin not found
    }

    // You can add a method to simulate daily fill level increase here
    public void simulateDailyFillIncrease() {
        List<Bin> bins = binRepository.findAll();
        for (Bin bin : bins) {
            if (bin.getStatus() == BinStatus.Empty) {
                bin.setStatus(BinStatus.Half);
            } else if (bin.getStatus() == BinStatus.Half) {
                bin.setStatus(BinStatus.Full);
            }
            bin.setLastUpdated(LocalDateTime.now());
            binRepository.save(bin); // Save each updated bin
        }
        System.out.println("Simulated daily bin fill level increase.");
    }

    // You might call simulateDailyFillIncrease periodically using Spring's @Scheduled annotation
    // (requires @EnableScheduling on your main application class)
}