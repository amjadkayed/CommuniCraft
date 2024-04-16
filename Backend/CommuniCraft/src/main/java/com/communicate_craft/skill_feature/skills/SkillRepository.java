package com.communicate_craft.skill_feature.skills;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findBySkillCategory_CategoryId(Long categoryId);

    Optional<Skill> findBySkillNameIgnoreCase(String skillName);
}
