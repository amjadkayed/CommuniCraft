package com.communicate_craft.location;

import java.util.Optional;

public interface LocationService {
    Location saveLocation(Location location);
    Optional<Location> findById(Integer locationId);
    Optional<Location> checkIfLocationExists(String cityName, String stateName, String countryName);
}
