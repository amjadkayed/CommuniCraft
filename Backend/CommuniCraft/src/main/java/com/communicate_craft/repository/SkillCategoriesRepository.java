package com.communicate_craft.repository;

import com.communicate_craft.model.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillCategoriesRepository extends JpaRepository<SkillCategory, Long> {
    Optional<SkillCategory> findByCategoryNameIgnoreCase(String categoryName);
}
