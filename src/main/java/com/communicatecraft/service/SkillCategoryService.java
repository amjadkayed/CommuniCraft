package com.communicatecraft.service;

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

    public List<SkillCategory> getAllSkillCategories() {
        return repository.findAll();
    }

    public Optional<SkillCategory> getSkillCategoryById(Integer id) {
        return repository.findById(id);
    }

    public SkillCategory saveOrUpdateSkillCategory(SkillCategory skillCategory) {
        return repository.save(skillCategory);
    }

    public void deleteSkillCategory(Integer id) {
        repository.deleteById(id);
    }
}
