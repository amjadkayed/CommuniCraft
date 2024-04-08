package com.communicatecraft.controller;

import com.communicatecraft.helper.Generator;
import com.communicatecraft.model.City;
import com.communicatecraft.service.CityService;
import com.communicatecraft.utils.ResponseMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<Object> getAllCities() {
        try {
            return ResponseEntity.ok(cityService.getAllCities());
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving cities: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Integer id) {
        return cityService.getCityById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createCity(@Valid @RequestBody City city, BindingResult result) {
        if (result.hasErrors()) {
            // return errors list
            return new ResponseEntity<>(Generator.bindingResultToErrorList(result), HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(cityService.saveCity(city), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error adding city: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCity(@PathVariable Integer id, @Valid @RequestBody City city,
                                             BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        city.setCityId(id);
        try {
            Optional<City> updatedCity = cityService.updateCity(city);
            return updatedCity.isPresent() ? new ResponseEntity<>(updatedCity, HttpStatus.OK)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating city: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCity(@PathVariable Integer id) {
        try {
            cityService.deleteCityById(id);
            return ResponseEntity.ok(new ResponseMessage("Category with id " + id + " was deleted successfully."));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
