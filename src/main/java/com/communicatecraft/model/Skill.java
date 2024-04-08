package com.communicatecraft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;


@Data
@Entity
@Table(name = "Skill")
public class Skill implements Serializable {


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

    //TODO: relation need fixes, when adding a skill to not existed category it adds it without any error
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "skillsCategoryId")
    private transient SkillCategory skillCategory;
}