package com.communicatecraft.service;

import com.communicatecraft.model.City;
import com.communicatecraft.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private static final String NOT_FOUND_MESSAGE = "City not found with id ";

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityById(Integer id) {
        return cityRepository.findById(id);
    }

    public Optional<City> saveCity(City city) {
        return Optional.of(cityRepository.save(city));
    }

    public Optional<City> updateCity(City city) {
        if (!cityRepository.existsById(city.getCityId())) {
            return Optional.empty();
        }
        return saveCity(city);
    }

    public void deleteCityById(Integer id) {
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + id);
        }
        cityRepository.deleteById(id);
    }
}
