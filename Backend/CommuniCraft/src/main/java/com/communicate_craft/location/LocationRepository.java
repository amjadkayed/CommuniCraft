package com.communicate_craft.location;

import com.communicate_craft.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByCityNameAndStateNameAndCountryName(String cityName, String stateName, String countryName);

}
