package com.communicate_craft.location;

import com.communicate_craft.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Locations")
public class Location {

    @Id
    @GeneratedValue
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
