package com.mycompany.capstone.smart_waste_app_backend.repository;

import com.mycompany.capstone.smart_waste_app_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom query method to find a user by username
    User findByUsername(String username);
}