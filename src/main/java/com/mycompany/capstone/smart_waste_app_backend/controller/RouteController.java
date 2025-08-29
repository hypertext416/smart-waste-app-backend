package com.mycompany.capstone.smart_waste_app_backend.controller;

import com.mycompany.capstone.smart_waste_app_backend.model.Route;
import com.mycompany.capstone.smart_waste_app_backend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    // GET /api/routes - Get all saved routes
    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    // GET /api/routes/{id} - Get a specific route by ID
    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Integer id) {
        return routeService.getRouteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/routes/optimize - Endpoint to trigger route optimization
    // This assumes a userId (e.g., admin) from the frontend
    @PostMapping("/optimize")
    public ResponseEntity<Route> optimizeRoute(@RequestBody Map<String, Object> requestBody) {
        try {
            List<String> binIds = (List<String>) requestBody.get("binIds");
            // For now, hardcode admin user ID, replace with actual authenticated user later
            Integer optimizedByUserId = 1; // Assuming admin_user has ID 1

            String routeName = (String) requestBody.getOrDefault("routeName", null);
            String description = (String) requestBody.getOrDefault("description", null);

            Route optimizedRoute = routeService.optimizeAndSaveRoute(binIds, optimizedByUserId, routeName, description);
            return new ResponseEntity<>(optimizedRoute, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}