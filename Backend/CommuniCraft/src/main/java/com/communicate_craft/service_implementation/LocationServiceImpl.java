package com.communicate_craft.service_implementation;

import com.communicate_craft.model.Location;
import com.communicate_craft.repository.LocationRepository;
import com.communicate_craft.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    public Optional<Location> checkIfLocationExists(String cityName, String stateName, String countryName) {
        log.info("LocationService --> checkIfLocationExists");
        Location existingLocation = locationRepository.findByCityNameAndStateNameAndCountryName(cityName, stateName, countryName);
        return Optional.ofNullable(existingLocation);
    }
    @Transactional
    public Location saveLocation(Location location) {
        log.info("LocationService --> saveLocation");
        // check if the location is already exists
        Optional<Location> checkResult = checkIfLocationExists(location.getCityName(), location.getStateName(), location.getCountryName());
        if (checkResult.isPresent()) {
            log.info("LocationService --> saveLocation --> location is already exists");
            return checkResult.get();
        }
        return locationRepository.save(location);
    }

    public Optional<Location> findById(Integer locationId) {
        log.info("LocationService --> findById");
        return locationRepository.findById(locationId);
    }


}
