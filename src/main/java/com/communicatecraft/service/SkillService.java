package com.communicatecraft.service;

import com.communicatecraft.model.Skill;
import com.communicatecraft.repository.SkillRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private static final String NOT_FOUND_MESSAGE = "Skill not found with id ";

    public List<Skill> getAllSkillsByCategoryId(Integer skillCategoryId) {
        return skillRepository.getAllSkillsBySkillCategoryId(skillCategoryId);
    }

    public Optional<Skill> getSkillById(Integer id) {
        return skillRepository.findById(id);
    }

    public Optional<Skill> saveSkill(Skill skill) {
        return Optional.of(skillRepository.save(skill));
    }

    public Optional<Skill> updateSkill(Skill skill) {
        if (skillRepository.existsById(skill.getSkillId())) {
            return saveSkill(skill);
        } else {
            return Optional.empty();
        }
    }

    public void deleteSkill(Integer id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + id);
        }
    }
}
