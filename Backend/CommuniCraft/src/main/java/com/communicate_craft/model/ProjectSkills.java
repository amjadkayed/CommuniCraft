package com.communicate_craft.model;

import com.communicate_craft.dto.ProjectSkillsDTO;
import com.communicate_craft.enums.Complexity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ProjectSkills")
@NoArgsConstructor
public class ProjectSkills {
    @Id
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "project_project_id", nullable = false)
    @JsonIgnore
    private Project project;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "skill_skill_id", nullable = false)
    private Skill skill;

    @Column(name = "NumberOfCrafters")
    private Integer numberOfCrafters;

    @Enumerated(EnumType.STRING)
    @Column(name = "Complexity")
    private Complexity complexity;

    @Column(name = "Salary")
    private Double salary;

    public ProjectSkills(ProjectSkillsDTO projectSkillsDTO, Project project) {
        setProject(project);
        setSkill(projectSkillsDTO.getSkill());
        setNumberOfCrafters(projectSkillsDTO.getNumberOfCrafters());
        setComplexity(projectSkillsDTO.getComplexity());
        setSalary(projectSkillsDTO.getSalary());
    }
}
