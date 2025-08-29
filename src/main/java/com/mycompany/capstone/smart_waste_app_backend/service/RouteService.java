package com.mycompany.capstone.smart_waste_app_backend.service;

import com.mycompany.capstone.smart_waste_app_backend.model.Bin;
import com.mycompany.capstone.smart_waste_app_backend.model.Route;
import com.mycompany.capstone.smart_waste_app_backend.model.RouteBin;
import com.mycompany.capstone.smart_waste_app_backend.model.User;
import com.mycompany.capstone.smart_waste_app_backend.repository.BinRepository;
import com.mycompany.capstone.smart_waste_app_backend.repository.RouteBinRepository;
import com.mycompany.capstone.smart_waste_app_backend.repository.RouteRepository;
import com.mycompany.capstone.smart_waste_app_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private RouteBinRepository routeBinRepository;
    @Autowired
    private BinRepository binRepository;
    @Autowired
    private UserRepository userRepository;

    // Helper class for route optimization
    private static class BinLocation {
        String id;
        String location;
        BigDecimal latitude;
        BigDecimal longitude;
        boolean visited = false;

        public BinLocation(String id, String location, BigDecimal latitude, BigDecimal longitude) {
            this.id = id;
            this.location = location;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double calculateDistance(BinLocation other) {
            final int R = 6371; // Radius of Earth in kilometers

            double lat1Rad = Math.toRadians(this.latitude.doubleValue());
            double lon1Rad = Math.toRadians(this.longitude.doubleValue());
            double lat2Rad = Math.toRadians(other.latitude.doubleValue());
            double lon2Rad = Math.toRadians(other.longitude.doubleValue());

            double dLat = lat2Rad - lat1Rad;
            double dLon = lon2Rad - lon1Rad;

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                       Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                       Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            return R * c; // Distance in kilometers
        }

        @Override
        public String toString() {
            return id + " (" + location + ")";
        }
    }

    @Transactional
    public Route optimizeAndSaveRoute(List<String> binIds, Integer optimizedByUserId, String routeName, String description) {
        if (binIds == null || binIds.size() < 2) {
            throw new IllegalArgumentException("At least two bins are required for route optimization.");
        }

        List<BinLocation> binsToOptimize = binRepository.findAllById(binIds).stream()
                .map(bin -> new BinLocation(bin.getBinId(), bin.getLocation(), bin.getLatitude(), bin.getLongitude()))
                .collect(Collectors.toList());

        if (binsToOptimize.size() != binIds.size()) {
            throw new IllegalArgumentException("One or more specified bins were not found.");
        }

        // Simple Nearest Neighbor algorithm
        List<BinLocation> optimizedPath = new ArrayList<>();
        BinLocation currentBin = binsToOptimize.get(0); // Start with the first selected bin
        currentBin.visited = true;
        optimizedPath.add(currentBin);

        while (optimizedPath.size() < binsToOptimize.size()) {
            BinLocation nearestBin = null;
            double minDistance = Double.MAX_VALUE;

            for (BinLocation bin : binsToOptimize) {
                if (!bin.visited) {
                    double distance = currentBin.calculateDistance(bin);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestBin = bin;
                    }
                }
            }
            if (nearestBin != null) {
                optimizedPath.add(nearestBin);
                nearestBin.visited = true;
                currentBin = nearestBin;
            } else {
                break; // Should not happen
            }
        }

        // Save the optimized route to the database
        Optional<User> userOptional = userRepository.findById(optimizedByUserId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Optimized by user not found.");
        }

        Route route = new Route();
        route.setRouteName(routeName != null && !routeName.isEmpty() ? routeName : "Optimized Route " + LocalDateTime.now());
        route.setDescription(description);
        route.setGeneratedAt(LocalDateTime.now());
        route.setOptimizedBy(userOptional.get());
        route = routeRepository.save(route); // Save to get generated ID

        // Save individual bins in the route order
        for (int i = 0; i < optimizedPath.size(); i++) {
            RouteBin routeBin = new RouteBin();
            routeBin.setRoute(route);
            // Ensure bin is fetched from repository to be managed by JPA
            routeBin.setBin(binRepository.findById(optimizedPath.get(i).id).orElseThrow(() -> new RuntimeException("Bin not found during route save")));
            routeBin.setSequenceOrder(i + 1);
            routeBinRepository.save(routeBin); // Corrected: save individual RouteBin
        }

        // Set the optimized path in the transient field for frontend response
        route.setOptimizedPath(optimizedPath.stream().map(bl -> bl.id).collect(Collectors.toList()));

        return route;
    }

    // Helper method to populate the optimizedPath for a given Route
    private Route populateOptimizedPath(Route route) {
        List<RouteBin> routeBins = routeBinRepository.findByRoute_RouteIdOrderBySequenceOrder(route.getRouteId());
        route.setOptimizedPath(routeBins.stream()
                                        .map(rb -> rb.getBin() != null ? rb.getBin().getBinId() : null)
                                        .filter(java.util.Objects::nonNull)
                                        .collect(Collectors.toList()));
        return route;
    }

    public List<Route> getAllRoutes() {
        List<Route> allRoutes = routeRepository.findAll();
        List<Route> populatedRoutes = new ArrayList<>();
        for (Route route : allRoutes) {
            populatedRoutes.add(populateOptimizedPath(route));
        }
        return populatedRoutes;
    }

    public Optional<Route> getRouteById(Integer routeId) {
        return routeRepository.findById(routeId)
                .map(route -> populateOptimizedPath(route));
    }
}