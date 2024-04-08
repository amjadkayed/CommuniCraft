package com.communicate_craft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LocationId")
    private Integer locationId;

    @NotEmpty(message = "empty city name")
    @Column(name = "CityName")
    private String cityName;

    @Column(name = "StateName")
    private String stateName;

    @NotEmpty(message = "empty country name")
    @Column(name = "CountryName")
    private String countryName;

    @OneToMany(mappedBy = "location", orphanRemoval = true)
    @JsonIgnore
    private List<User> users = new ArrayList<>();

}
