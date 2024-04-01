package com.communicatecraft.controller;

import com.communicatecraft.helper.Generator;
import com.communicatecraft.model.City;
import com.communicatecraft.service.CityService;
import com.communicatecraft.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error retrieving cities: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCityById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(cityService.getCityById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error retrieving city with id " + id + ": " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCity(@Valid @RequestBody City city, BindingResult result) {
        if (result.hasErrors()) {
            // return errors list
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(cityService.saveCity(city));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error adding city: " + e.getMessage()));
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
            return ResponseEntity.ok(cityService.updateCity(city));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error updating city with id " + id + ": " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCity(@PathVariable Integer id) {
        try {
            cityService.deleteCityById(id);
            return ResponseEntity.ok(new ResponseMessage("Category with id " + id + "was deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error deleting city with id " + id + ": " + e.getMessage()));
        }
    }
}
