package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.repository.SkillCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillCategoryService {

    private final SkillCategoryRepository skillCategoryRepository;
    private String notFoundMessage = "Skill category not found with id ";

    public List<SkillCategory> getAllSkillCategories() {
        return skillCategoryRepository.findAll();
    }

    public SkillCategory getSkillCategoryById(Integer id) {
        return skillCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(notFoundMessage + id));
    }

    public SkillCategory saveSkillCategory(SkillCategory skillCategory) {
        try {
            return skillCategoryRepository.save(skillCategory);
        } catch (Exception e) {
            throw new DuplicatedFieldException("Duplicated in skill category name or error in the database");
        }
    }

    public SkillCategory updateSkillCategory(SkillCategory skillCategory) {
        if (skillCategoryRepository.existsById(skillCategory.getSkillsCategoryId())) {
            return saveSkillCategory(skillCategory);
        } else {
            throw new EntityNotFoundException(notFoundMessage + skillCategory.getSkillsCategoryId());
        }
    }

    public void deleteSkillCategory(Integer id) {
        if (skillCategoryRepository.existsById(id)) {
            skillCategoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(notFoundMessage + id);
        }
    }
}
