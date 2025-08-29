package com.mycompany.capstone.smart_waste_app_backend.controller;

import com.mycompany.capstone.smart_waste_app_backend.model.Schedule;
import com.mycompany.capstone.smart_waste_app_backend.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // GET /api/schedules - Get all schedules
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // GET /api/schedules/{id} - Get a single schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Integer id) {
        return scheduleService.getScheduleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/schedules - Add a new schedule (Admin access would be applied here)
    @PostMapping
    public ResponseEntity<Schedule> addSchedule(@RequestBody Schedule schedule) {
        // In a real app, you'd verify admin role before this action
        Schedule newSchedule = scheduleService.addSchedule(schedule);
        return new ResponseEntity<>(newSchedule, HttpStatus.CREATED);
    }

    // PUT /api/schedules/{id} - Update an existing schedule (Admin access)
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer id, @RequestBody Schedule schedule) {
        // In a real app, you'd verify admin role
        Schedule updatedSchedule = scheduleService.updateSchedule(id, schedule);
        if (updatedSchedule != null) {
            return ResponseEntity.ok(updatedSchedule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/schedules/{id} - Delete a schedule (Admin access)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content for successful deletion
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        // In a real app, you'd verify admin role
        if (scheduleService.deleteSchedule(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}