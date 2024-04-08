package com.communicate_craft.controller;

import com.communicate_craft.model.Location;
import com.communicate_craft.service.LocationService;
import com.communicate_craft.utils.Converter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Object> createLocation(@Valid @RequestBody Location location, BindingResult result) {
        if(result.hasErrors()){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errors", Converter.convertBindingResultToErrorList(result));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationService.saveLocation(location));
    }

}
