package com.communicate_craft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Locations")
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "LocationId")
    private Long id;

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

    public Location(String cityName, String stateName, String countryName) {
        this.cityName = cityName;
        this.stateName = stateName;
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "Location{" +
                "cityName='" + cityName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
