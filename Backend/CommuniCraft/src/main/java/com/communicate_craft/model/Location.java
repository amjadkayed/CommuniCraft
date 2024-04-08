package com.communicate_craft.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationID;

    @Column(nullable = false)
    private String cityName;

    private String stateName;

    @Column(nullable = false)
    private String countryName;
}
