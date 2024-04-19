package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.SkillDTO;
import com.communicate_craft.model.Skill;
import com.communicate_craft.model.SkillCategory;
import com.communicate_craft.repository.SkillRepository;
import com.communicate_craft.service.SkillCategoryService;
import com.communicate_craft.service.SkillService;
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
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillCategoryService skillCategoryService;

    private SkillCategory checkCategoryExists(Long categoryId) {
        Optional<SkillCategory> category = skillCategoryService.getById(categoryId);
        if (category.isEmpty())
            throw new EntityNotFoundException("Category not found with id: " + categoryId);
        return category.get();
    }

    private Skill checkCategoryAndConvertToSkill(SkillDTO skillDTO) {
        return new Skill(skillDTO, checkCategoryExists(skillDTO.getCategoryId()));
    }

    private void checkIfExistsById(Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (skill.isEmpty())
            throw new EntityNotFoundException("Skill with ID = " + skillId + " is not found");
    }

    @Override
    public List<Skill> getSkillsByCategoryId(Long categoryId) {
        log.info("SkillService --> getSkillsForCategory");
        checkCategoryExists(categoryId);
        return skillRepository.findBySkillCategory_CategoryId(categoryId);
    }

    @Transactional
    @Override
    public Skill addSkill(SkillDTO request) {
        log.info("SkillService --> addSkill");
        Skill skill = checkCategoryAndConvertToSkill(request);
        // check if there is a skill with the same name
        Optional<Skill> checkResult = skillRepository.findBySkillNameIgnoreCase(skill.getSkillName());
        return checkResult.orElseGet(() -> skillRepository.save(skill));
    }

    @Override
    public Skill updateSkill(SkillDTO request) {
        log.info("SkillService --> UpdateSkill");
        checkIfExistsById(request.getSkillId());
        Skill skill = checkCategoryAndConvertToSkill(request);
        // check if there is a skill with the same name
        Optional<Skill> checkResult = skillRepository.findBySkillNameIgnoreCase(skill.getSkillName());
        if (checkResult.isPresent() && !checkResult.get().getSkillId().equals(skill.getSkillId()))
            throw new IllegalArgumentException("Invalid skill name");
        return skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(Long skillId) {
        log.info("SkillService --> deleteSkill");
        checkIfExistsById(skillId);
        skillRepository.deleteById(skillId);
    }
}
