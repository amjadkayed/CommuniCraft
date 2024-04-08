package com.communicate_craft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationID;

    @NotNull(message = "null city name")
    private String cityName;

    private String stateName;

    @NotNull(message = "null country name")
    private String countryName;
}
