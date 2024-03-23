package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.repository.SkillCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for SkillCategory.
 * This class provides methods to perform CRUD operations on SkillCategory objects.
 * The @Service annotation is used to indicate that this class is a service provider.
 * The @RequiredArgsConstructor annotation is used to automatically generate a constructor with required arguments.
 */
@Service
@RequiredArgsConstructor
public class SkillCategoryService {

    /**
     * The repository that this service will use to interact with the database.
     */
    private final SkillCategoryRepository skillCategoryRepository;

    /**
     * The message that will be used when a SkillCategory object is not found.
     */
    private String notFoundMessage = "Skill category not found with id ";

    /**
     * Retrieves all SkillCategory objects from the database.
     * @return a list of all SkillCategory objects.
     */
    public List<SkillCategory> getAllSkillCategories() {
        return skillCategoryRepository.findAll();
    }

    /**
     * Retrieves a SkillCategory object by its id.
     * @param id the id of the SkillCategory object to retrieve.
     * @return the SkillCategory object with the given id.
     * @throws EntityNotFoundException if no SkillCategory object with the given id is found.
     */
    public SkillCategory getSkillCategoryById(Integer id) {
        return skillCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(notFoundMessage + id));
    }

    /**
     * Saves a SkillCategory object to the database.
     * @param skillCategory the SkillCategory object to save.
     * @return the saved SkillCategory object.
     * @throws DuplicatedFieldException if a SkillCategory object with the same name already exists in the database.
     */
    public SkillCategory saveSkillCategory(SkillCategory skillCategory) {
        try {
            return skillCategoryRepository.save(skillCategory);
        } catch (Exception e) {
            throw new DuplicatedFieldException("Duplicated in skill category name or error in the database");
        }
    }

    /**
     * Updates a SkillCategory object in the database.
     * @param skillCategory the SkillCategory object to update.
     * @return the updated SkillCategory object.
     * @throws EntityNotFoundException if no SkillCategory object with the given id is found.
     */
    public SkillCategory updateSkillCategory(SkillCategory skillCategory) {
        if (skillCategoryRepository.existsById(skillCategory.getSkillsCategoryId())) {
            return saveSkillCategory(skillCategory);
        } else {
            throw new EntityNotFoundException(notFoundMessage + skillCategory.getSkillsCategoryId());
        }
    }

    /**
     * Deletes a SkillCategory object from the database.
     * @param id the id of the SkillCategory object to delete.
     * @throws EntityNotFoundException if no SkillCategory object with the given id is found.
     */
    public void deleteSkillCategory(Integer id) {
        if (skillCategoryRepository.existsById(id)) {
            skillCategoryRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(notFoundMessage + id);
        }
    }
}