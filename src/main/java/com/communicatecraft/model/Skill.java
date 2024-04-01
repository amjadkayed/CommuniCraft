package com.communicatecraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a skill that can be associated with a category.
 * Each skill is associated with one category.
 * This class implements Serializable, which means it can be converted to a byte stream.
 */
@Data
@Entity
@Table(name = "Skill")
public class Skill implements Serializable {

    /**
     * The unique identifier of the skill.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillId;

    /**
     * The name of the skill.
     * It must be non-null, non-empty, non-blank and less than 129 characters.
     */
    @Size(max = 128, message = "Skill name must be less than 129 characters")
    @NotNull(message = "null skill name")
    @NotBlank(message = "blank skill name")
    @NotEmpty(message = "empty skill name")
    @Column(nullable = false, length = 128)
    private String skillName;

    /**
     * The identifier of the category that this skill belongs to.
     * It must be non-null.
     */
    @Column(nullable = false)
    private Integer skillCategoryId;

    //TODO: relation need fixes, when adding a skill to not existed category it adds it without any error
    /**
     * The category that this skill belongs to.
     * It is fetched eagerly, meaning it will be fetched when the Skill object is fetched.
     * It is also ignored when the Skill object is serialized to JSON.
     * The 'transient' keyword is used to indicate that this field should not be serialized.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "skillsCategoryId")
    @JsonIgnore
    private transient SkillCategory skillCategory;
}