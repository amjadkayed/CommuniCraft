package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.City;
import com.communicatecraft.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for City.
 * This class provides methods for performing CRUD operations on City objects.
 * The @Service annotation is used to indicate that this class is a service.
 * The @RequiredArgsConstructor annotation is used to automatically generate a constructor with required arguments.
 */
@Service
@RequiredArgsConstructor
public class CityService {

    /**
     * The repository that this service will use to interact with the database.
     */
    private final CityRepository cityRepository;
    private static final String NOT_FOUND_MESSAGE = "City not found with id ";

    /**
     * Retrieves all City objects.
     * @return a List containing all City objects.
     */
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    /**
     * Retrieves a City object by its id.
     * @param id the id of the City object to retrieve.
     * @return the City object with the specified id.
     * @throws EntityNotFoundException if no City object with the specified id is found.
     */
    public City getCityById(Integer id) {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
    }

    /**
     * Creates a new City object or updates an existing one.
     * @param city the City object to create or update.
     * @return the created or updated City object.
     * @throws DuplicatedFieldException if a City object with the same name already exists.
     */
    public City saveCity(City city) {
        try {
            return cityRepository.save(city);
        } catch (Exception e) {
            throw new DuplicatedFieldException("Duplicated in city name or error in the database");
        }
    }

    /**
     * Updates a City object.
     * @param city the City object with the updated data.
     * @return the updated City object.
     * @throws EntityNotFoundException if no City object with the specified id is found.
     */
    public City updateCity(City city) {
        if (cityRepository.existsById(city.getCityId())) {
            return saveCity(city);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + city.getCityId());
        }
    }

    /**
     * Deletes a City object.
     * @param id the id of the City object to delete.
     * @throws EntityNotFoundException if no City object with the specified id is found.
     */
    public void deleteCityById(Integer id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + id);
        }
    }
}