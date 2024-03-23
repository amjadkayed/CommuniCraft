package com.communicatecraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillId;

    @Size(max = 128, message = "Skill name must be less than 129 characters")
    @NotNull(message = "null skill name")
    @NotBlank(message = "blank skill name")
    @NotEmpty(message = "empty skill name")
    @Column(nullable = false, length = 128)
    private String skillName;

    @Column(nullable = false)
    private Integer skillCategoryId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "skillsCategoryId")
    @JsonIgnore
    private SkillCategory skillCategory;
}
