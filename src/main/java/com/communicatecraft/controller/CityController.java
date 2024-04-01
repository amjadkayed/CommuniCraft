package com.communicatecraft.controller;

import com.communicatecraft.helper.Generator;
import com.communicatecraft.model.ApiResponse;
import com.communicatecraft.model.City;
import com.communicatecraft.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


/**
 * Controller class for City.
 * This class provides RESTful endpoints for performing CRUD operations on City objects.
 * The @RestController annotation is used to indicate that this class is a REST controller.
 * The @RequestMapping annotation is used to specify the base URL for all endpoints in this class.
 * The @RequiredArgsConstructor annotation is used to automatically generate a constructor with required arguments.
 */
@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    /**
     * The service that this controller will use to interact with the database.
     */
    private final CityService cityService;

    /**
     * Retrieves all City objects.
     *
     * @return a List containing all City objects as data in an ApiResponse with error message to handle the error in
     * retrieving requested data.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<City>>> getAllCities() {
        try {
            return ResponseEntity.ok(new ApiResponse<>(cityService.getAllCities(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving cities: " + e.getMessage())));
        }
    }

    /**
     * Retrieves a City object by its id.
     *
     * @param id the id of the City object to retrieve.
     * @return the City object with the specified id as data in an ApiResponse with error message if the required id is
     * not exist or if an error occurs while retrieving it.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<City>> getCityById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(cityService.getCityById(id), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null,
                            Collections.singletonList("Error retrieving city with id " + id + ": " + e.getMessage())));
        }
    }

    /**
     * Creates a new City object.
     *
     * @param city the City object to create.
     * @return the created City object as data in an ApiResponse with error message if city's
     * information is not valid as defined in the entity, or if saving process threw an error.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<City>> createCity(@Valid @RequestBody City city, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, Generator.bindingResultToErrorList(result)));
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(cityService.saveCity(city), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList(e.getMessage())));
        }
    }

    /**
     * Updates a City object.
     *
     * @param id   the id of the City object to update.
     * @param city the City object with the updated data.
     * @return the updated City object as data in an ApiResponse with error message if city's
     * information is not valid as defined in the entity, or if saving process threw an error.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<City>> updateCity(@PathVariable Integer id, @Valid @RequestBody City city,
                                                        BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, Generator.bindingResultToErrorList(result)));
        }
        city.setCityId(id);
        try {
            return ResponseEntity.ok(new ApiResponse<>(cityService.updateCity(city), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList(e.getMessage())));
        }
    }

    /**
     * Deletes a City object.
     *
     * @param id the id of the City object to delete.
     * @return a boolean value indicating whether the deletion was successful or not as data in an ApiResponse with error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteCity(@PathVariable Integer id) {
        try {
            cityService.deleteCityById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, Collections.singletonList(e.getMessage())));
        }
    }
}
