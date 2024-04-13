package com.communicate_craft.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        log.info("LocationService --> saveLocation");
        return locationRepository.save(location);
    }

    public Optional<Location> findById(Integer locationId) {
        log.info("LocationService --> findById");
        return locationRepository.findById(locationId);
    }

    public Optional<Location> checkIfLocationExists(String cityName, String stateName, String countryName) {
        log.info("LocationService --> checkIfLocationExists");
        Location existingLocation = locationRepository.findByCityNameAndStateNameAndCountryName(cityName, stateName, countryName);
        return Optional.ofNullable(existingLocation);
    }
}
