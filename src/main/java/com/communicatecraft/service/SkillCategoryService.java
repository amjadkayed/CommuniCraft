package com.communicatecraft.service;

import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.repository.SkillCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillCategoryService {

    private final SkillCategoryRepository skillCategoryRepository;
    private static final String NOT_FOUND_MESSAGE = "Skill category not found with id ";

    public List<SkillCategory> getAllSkillCategories() {
        return skillCategoryRepository.findAll();
    }

    public Optional<SkillCategory> getSkillCategoryById(Integer id) {
        return skillCategoryRepository.findById(id);
    }

    public Optional<SkillCategory> saveSkillCategory(SkillCategory skillCategory) {
        return Optional.of(skillCategoryRepository.save(skillCategory));
    }

    public Optional<SkillCategory> updateSkillCategory(SkillCategory skillCategory) {
        if (skillCategoryRepository.existsById(skillCategory.getSkillsCategoryId())) {
            return saveSkillCategory(skillCategory);
        } else {
            return Optional.empty();
        }
    }

    public void deleteSkillCategory(Integer id) {
        if (skillCategoryRepository.existsById(id)) {
            skillCategoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + id);
        }
    }
}
