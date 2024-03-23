package com.communicatecraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the CommunicateCraft application.
 * This class is responsible for starting the Spring Boot application.
 * The @SpringBootApplication annotation is used to mark a configuration class that declares one or more @Bean methods and also triggers auto-configuration and component scanning.
 */
@SpringBootApplication
public class CommunicateCraftApplication {

    /**
     * The main method serves as the entry point for the application.
     * The SpringApplication.run() method is called to bootstrap the application.
     * @param args command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(CommunicateCraftApplication.class, args);
    }

}