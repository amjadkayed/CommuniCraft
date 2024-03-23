package com.communicatecraft.repository;

import com.communicatecraft.model.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for SkillCategory.
 * This interface extends JpaRepository which provides JPA related methods such as save(), findOne(), findAll(), count(), delete() etc.
 * By extending JpaRepository, this interface inherits all its methods and we can use it directly in our service implementation.
 * The @Repository annotation is a marker for any class that fulfills the role of repository or Data Access Object.
 * This annotation is a general-purpose stereotype and individual teams may narrow their semantics and use as appropriate.
 */
@Repository
public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Integer> {
    // This will automatically implement the basic CRUD operations
}