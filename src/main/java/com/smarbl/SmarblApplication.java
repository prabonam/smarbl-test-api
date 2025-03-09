package com.smarbl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Smarbl Spring Boot application.
 * This class bootstraps and launches the application.
 */
@SpringBootApplication
public class SmarblApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(SmarblApplication.class, args);
    }
}
