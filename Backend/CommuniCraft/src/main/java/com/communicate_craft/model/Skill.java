package com.communicate_craft.model;

import com.communicate_craft.dto.SkillDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Skills")
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue
    @Column(name = "SkillId")
    private Long skillId;

    @NotEmpty(message = "empty skill name")
    @Column(name = "SkillName")
    private String skillName;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "skill_category_category_id", nullable = false)
    private SkillCategory skillCategory;

    public Skill(String skillName) {
        this.skillName = skillName;
    }

    public Skill(SkillDTO skillDTO, SkillCategory category){
        setSkillId(skillDTO.getSkillId());
        setSkillCategory(category);
        setSkillName(skillDTO.getSkillName());
    }
}
