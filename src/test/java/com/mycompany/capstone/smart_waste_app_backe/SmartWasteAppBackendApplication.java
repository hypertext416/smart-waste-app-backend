package com.mycompany.capstone.smart_waste_app_backe; // Your confirmed root package

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // This annotation enables component scanning
public class SmartWasteAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartWasteAppBackendApplication.class, args);
    }
}