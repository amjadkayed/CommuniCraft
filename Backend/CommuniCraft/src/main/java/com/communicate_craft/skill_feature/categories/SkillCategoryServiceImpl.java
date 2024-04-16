package com.communicate_craft.skill_feature.categories;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillCategoryServiceImpl implements SkillCategoryService {
    private final SkillCategoriesRepository categoriesRepository;

    private void checkIfExistsById(Long categoryId) {
        Optional<SkillCategory> skillCategory = categoriesRepository.findById(categoryId);
        if (skillCategory.isEmpty())
            throw new EntityNotFoundException("Category with ID = " + categoryId + " is not found");
    }

    @Override
    public Optional<SkillCategory> getById(Long categoryId) {
        return categoriesRepository.findById(categoryId);
    }

    @Override
    public List<SkillCategory> getAllCategories() {
        log.info("SkillCategoryService --> getAllCategories");
        return categoriesRepository.findAll();
    }

    @Transactional
    @Override
    public SkillCategory addCategory(SkillCategory skillCategory) {
        log.info("SkillCategoryService --> addCategory");
        // check if there is a category with the same name
        Optional<SkillCategory> category = categoriesRepository.findByCategoryNameIgnoreCase(skillCategory.getCategoryName());
        return category.orElseGet(() -> categoriesRepository.save(skillCategory));
    }

    @Override
    public SkillCategory updateCategory(SkillCategory skillCategory) {
        log.info("SkillCategoryService --> updateCategory");
        checkIfExistsById(skillCategory.getCategoryId());
        // check if there is a category with the same name
        Optional<SkillCategory> category = categoriesRepository.findByCategoryNameIgnoreCase(skillCategory.getCategoryName());
        if (category.isPresent() && !category.get().getCategoryId().equals(skillCategory.getCategoryId()))
            throw new IllegalArgumentException("Invalid category name");
        return categoriesRepository.save(skillCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        log.info("SkillCategoryService --> deleteCategory");
        checkIfExistsById(categoryId);
        categoriesRepository.deleteById(categoryId);
    }
}
