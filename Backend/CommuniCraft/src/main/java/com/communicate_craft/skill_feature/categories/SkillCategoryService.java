package com.communicate_craft.skill_feature.categories;

import java.util.List;
import java.util.Optional;

public interface SkillCategoryService {
    Optional<SkillCategory> getById(Long categoryId);
    List<SkillCategory> getAllCategories();
    SkillCategory addCategory(SkillCategory skillCategory);
    SkillCategory updateCategory(SkillCategory skillCategory);
    void deleteCategory(Long categoryId);
}
