package com.communicate_craft.service;

import com.communicate_craft.model.Location;
import com.communicate_craft.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Optional<Location> findById(Integer locationId) {
        return locationRepository.findById(locationId);
    }

    public Optional<Location> checkIfLocationExists(String cityName, String stateName, String countryName) {
        Location existingLocation = locationRepository.findByCityNameAndStateNameAndCountryName(cityName, stateName, countryName);
        return Optional.ofNullable(existingLocation);
    }
}
