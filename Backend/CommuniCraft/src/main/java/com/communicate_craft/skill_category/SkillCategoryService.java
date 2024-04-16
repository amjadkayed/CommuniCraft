package com.communicate_craft.skill_category;

import java.util.List;

public interface SkillCategoryService {
    List<SkillCategory> getAllCategories();
    SkillCategory addCategory(SkillCategory skillCategory);
    SkillCategory updateCategory(SkillCategory skillCategory);
    void deleteCategory(Long categoryId);
}
