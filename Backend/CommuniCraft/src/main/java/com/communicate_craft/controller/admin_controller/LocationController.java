package com.communicate_craft.controller.admin_controller;

import com.communicate_craft.model.Location;
import com.communicate_craft.service.LocationService;
import com.communicate_craft.utility.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Object> createLocation(@Valid @RequestBody Location location, BindingResult result) {
        log.info("LocationController --> creating location: {}", location);
        Validator.validateBody(result);
        Location savedLocation = locationService.saveLocation(location);
        log.info("Location created successfully with ID: {}", savedLocation.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocation);
    }

}
