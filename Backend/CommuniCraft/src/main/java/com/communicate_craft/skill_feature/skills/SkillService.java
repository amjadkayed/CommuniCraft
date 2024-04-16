package com.communicate_craft.skill_feature.skills;

import java.util.List;

public interface SkillService {
    List<Skill> getSkillsByCategoryId(Long categoryId);

    Skill addSkill(SkillDTO request);

    Skill updateSkill(SkillDTO request);

    void deleteSkill(Long skillId);
}
