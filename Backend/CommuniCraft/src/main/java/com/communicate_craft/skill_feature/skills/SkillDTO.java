package com.communicate_craft.skill_feature.skills;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO extends Skill {
    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    public SkillDTO(String skillName, Long categoryId) {
        super(skillName);
        this.categoryId = categoryId;
    }
}
