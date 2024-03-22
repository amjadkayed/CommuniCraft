package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.repository.SkillCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillCategoryService {

    private final SkillCategoryRepository repository;
    private String notFoundMessage = "SkillCategory not found with id ";
    public List<SkillCategory> getAllSkillCategories() {
        return repository.findAll();
    }

    public Optional<SkillCategory> getSkillCategoryById(Integer id) {
        Optional<SkillCategory> skillCategory = repository.findById(id);
        if(skillCategory.isPresent()) {
            return skillCategory;
        } else {
            throw new EntityNotFoundException(notFoundMessage + id);
        }
    }

    public SkillCategory saveSkillCategory(SkillCategory skillCategory) {
        try {
            return repository.save(skillCategory);
        } catch (Exception e) {
            throw new DuplicatedFieldException("Duplicated in skill category name or error in the database");
        }
    }

    public SkillCategory updateSkillCategory(SkillCategory skillCategory) {
        if (repository.existsById(skillCategory.getSkillsCategoryId())) {
            return saveSkillCategory(skillCategory);
        } else {
            throw new EntityNotFoundException(notFoundMessage + skillCategory.getSkillsCategoryId());
        }
    }

    public void deleteSkillCategory(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException(notFoundMessage + id);
        }
    }
}
