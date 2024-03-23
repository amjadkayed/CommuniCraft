package com.communicatecraft.repository;

import com.communicatecraft.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Skill.
 * This interface extends JpaRepository which provides JPA related methods such as save(), findOne(), findAll(), count(), delete() etc.
 * By extending JpaRepository, this interface inherits all its methods and we can use it directly in our service implementation.
 * The @Repository annotation is a marker for any class that fulfills the role of repository or Data Access Object.
 * This annotation is a general-purpose stereotype and individual teams may narrow their semantics and use as appropriate.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    /**
     * This method is used to fetch all skills associated with a specific category.
     * The category is identified by its unique identifier (skillCategoryId).
     * The method returns a list of Skill objects.
     * If no skills are found for the given category, the method returns an empty list.
     */
    List<Skill> getAllSkillsBySkillCategoryId(Integer skillCategoryId);
}