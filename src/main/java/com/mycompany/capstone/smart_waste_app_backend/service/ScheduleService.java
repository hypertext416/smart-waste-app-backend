package com.mycompany.capstone.smart_waste_app_backend.service;

import com.mycompany.capstone.smart_waste_app_backend.model.Schedule;
import com.mycompany.capstone.smart_waste_app_backend.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Integer id) {
        return scheduleRepository.findById(id);
    }

    public Schedule addSchedule(Schedule schedule) {
        // You might add validation here before saving
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Integer id, Schedule updatedSchedule) {
        return scheduleRepository.findById(id).map(schedule -> {
            schedule.setRouteName(updatedSchedule.getRouteName());
            schedule.setArea(updatedSchedule.getArea());
            schedule.setCollectionDay(updatedSchedule.getCollectionDay());
            schedule.setCollectionTime(updatedSchedule.getCollectionTime());
            return scheduleRepository.save(schedule);
        }).orElse(null); // Return null if schedule not found
    }

    public boolean deleteSchedule(Integer id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}