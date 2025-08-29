package com.mycompany.capstone.smart_waste_app_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // Make sure this import is present

@SpringBootApplication
@ComponentScan(basePackages = "com.mycompany.capstone.smart_waste_app_backend") // ADD THIS EXACT LINE
public class SmartWasteAppBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartWasteAppBackendApplication.class, args);
    }
}