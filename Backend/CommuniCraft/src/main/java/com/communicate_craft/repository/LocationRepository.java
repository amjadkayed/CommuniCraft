package com.communicate_craft.repository;

import com.communicate_craft.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByCityNameAndStateNameAndCountryName(String cityName, String stateName, String countryName);

}
