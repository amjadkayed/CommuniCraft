package com.communicate_craft.location;

import com.communicate_craft.utils.Converter;
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

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/public/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationServiceImpl locationServiceImpl;

    @PostMapping
    public ResponseEntity<Object> createLocation(@Valid @RequestBody Location location, BindingResult result) {
        log.info("LocationController --> createLocation --> creating a new location");
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        // check if the location is already exists
        Optional<Location> checkResult = locationServiceImpl.checkIfLocationExists(location.getCityName(), location.getStateName(), location.getCountryName());
        if (checkResult.isPresent()) {
            log.info("LocationController --> createLocation --> location is already exists");
            return ResponseEntity.ok(checkResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationServiceImpl.saveLocation(location));
    }

}
