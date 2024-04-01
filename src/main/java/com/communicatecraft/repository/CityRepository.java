package com.communicatecraft.repository;

import com.communicatecraft.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for City.
 * This interface extends JpaRepository and provides methods for performing CRUD operations on City objects.
 * The @Repository annotation is used to indicate that this interface is a repository.
 * By extending JpaRepository, this interface inherits several methods for interacting with the database such as save(), delete(), findById() etc.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}