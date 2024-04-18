package com.communicate_craft.service;

import com.communicate_craft.model.Location;

import java.util.Optional;

public interface LocationService {
    Location saveLocation(Location location);
    Optional<Location> findById(Integer locationId);
    Optional<Location> checkIfLocationExists(String cityName, String stateName, String countryName);
}
