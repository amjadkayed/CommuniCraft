package com.communicate_craft.skill_category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillCategoriesRepository extends JpaRepository<SkillCategory, Long> {
    Optional<SkillCategory> findByCategoryNameIgnoreCase(String categoryName);
}
