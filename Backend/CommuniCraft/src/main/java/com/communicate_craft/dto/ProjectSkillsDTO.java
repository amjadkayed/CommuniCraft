package com.communicate_craft.dto;

import com.communicate_craft.enums.Complexity;
import com.communicate_craft.model.Skill;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSkillsDTO {
    private Skill skill;
    private Integer numberOfCrafters;
    private Complexity complexity;
    private Double salary;
}
