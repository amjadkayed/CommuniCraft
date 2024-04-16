package com.communicate_craft;

import com.communicate_craft.authentication.AuthenticationServiceImpl;
import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.location.Location;
import com.communicate_craft.location.LocationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class CommunicateCraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunicateCraftApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(LocationServiceImpl locationService, AuthenticationServiceImpl authenticationService) {
        return args -> {
            log.info("---------- The application has started on port 1218 ----------");
            // add some locations
            locationService.saveLocation(new Location("Nablus", "West-Bank", "Palestine"));
            locationService.saveLocation(new Location("Jerusalem", "West-Bank", "Palestine"));
            locationService.saveLocation(new Location("Jerico", "West-Bank", "Palestine"));
            locationService.saveLocation(new Location("Gaza", "Gaza-Stripe", "Palestine"));
            locationService.saveLocation(new Location("Rafah", "Gaza-Stripe", "Palestine"));
            log.info("locations were added successfully :)");

        };
    }
}
