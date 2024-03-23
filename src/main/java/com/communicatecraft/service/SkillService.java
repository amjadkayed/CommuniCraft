package com.communicatecraft.service;

import com.communicatecraft.exceptions.DuplicatedFieldException;
import com.communicatecraft.exceptions.EntityNotFoundException;
import com.communicatecraft.model.Skill;
import com.communicatecraft.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private String notFoundMessage = "Skill not found with id ";

    public List<Skill> getAllSkillsByCategoryId(Integer skillCategoryId) {
        return skillRepository.getAllSkillsBySkillCategoryId(skillCategoryId);
    }

    public Skill getSkillById(Integer id) {
        return skillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(notFoundMessage + id));
    }

    public Skill saveSkill(Skill skill) {
        try {
            return skillRepository.save(skill);
        } catch (Exception e) {
            throw new DuplicatedFieldException("Duplicated in skill name or error in the database");
        }
    }

    public Skill updateSkill(Skill skill) {
        if (skillRepository.existsById(skill.getSkillId())) {
            return saveSkill(skill);
        } else {
            throw new EntityNotFoundException(notFoundMessage + skill.getSkillId());
        }
    }

    public void deleteSkill(Integer id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(notFoundMessage + id);
        }
    }
}
