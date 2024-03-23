package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.Skill;
import com.communicatecraft.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Skill.
 * This class provides methods to perform CRUD operations on Skill objects.
 * The @Service annotation is used to indicate that this class is a service provider.
 * The @RequiredArgsConstructor annotation is used to automatically generate a constructor with required arguments.
 */
@Service
@RequiredArgsConstructor
public class SkillService {

    /**
     * The repository that this service will use to interact with the database.
     */
    private final SkillRepository skillRepository;

    /**
     * The message that will be used when a Skill object is not found.
     */
    private String notFoundMessage = "Skill not found with id ";

    /**
     * Retrieves all Skill objects associated with a specific category from the database.
     * @param skillCategoryId the id of the category to retrieve the skills for.
     * @return a list of all Skill objects associated with the specified category.
     */
    public List<Skill> getAllSkillsByCategoryId(Integer skillCategoryId) {
        return skillRepository.getAllSkillsBySkillCategoryId(skillCategoryId);
    }

    /**
     * Retrieves a Skill object by its id.
     * @param id the id of the Skill object to retrieve.
     * @return the Skill object with the given id.
     * @throws EntityNotFoundException if no Skill object with the given id is found.
     */
    public Skill getSkillById(Integer id) {
        return skillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(notFoundMessage + id));
    }

    /**
     * Saves a Skill object to the database.
     * @param skill the Skill object to save.
     * @return the saved Skill object.
     * @throws DuplicatedFieldException if a Skill object with the same name already exists in the database.
     */
    public Skill saveSkill(Skill skill) {
        try {
            return skillRepository.save(skill);
        } catch (Exception e) {
            throw new DuplicatedFieldException("Duplicated in skill name or error in the database");
        }
    }

    /**
     * Updates a Skill object in the database.
     * @param skill the Skill object to update.
     * @return the updated Skill object.
     * @throws EntityNotFoundException if no Skill object with the given id is found.
     */
    public Skill updateSkill(Skill skill) {
        if (skillRepository.existsById(skill.getSkillId())) {
            return saveSkill(skill);
        } else {
            throw new EntityNotFoundException(notFoundMessage + skill.getSkillId());
        }
    }

    /**
     * Deletes a Skill object from the database.
     * @param id the id of the Skill object to delete.
     * @throws EntityNotFoundException if no Skill object with the given id is found.
     */
    public void deleteSkill(Integer id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(notFoundMessage + id);
        }
    }
}