package com.communicate_craft.service;

import com.communicate_craft.dto.SkillDTO;
import com.communicate_craft.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkillsByCategoryId(Long categoryId);

    Skill addSkill(SkillDTO request);

    Skill updateSkill(SkillDTO request);

    void deleteSkill(Long skillId);
}
