package com.communicatecraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a category of skills.
 * Each category can contain multiple skills.
 * This class implements Serializable, which means it can be converted to a byte stream.
 */
@Data
@Entity
@Table(name = "SkillCategory")
public class SkillCategory implements Serializable {

    /**
     * The unique identifier of the skill category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillsCategoryId;

    /**
     * The name of the skill category.
     * It must be non-null, non-empty, non-blank and less than 129 characters.
     */
    @Size(max = 128, message = "Skill category name must be less than 129 characters")
    @NotNull(message = "null category name")
    @NotBlank(message = "blank category name")
    @NotEmpty(message = "empty category name")
    @Column(length = 128, nullable = false, unique = true)
    private String skillsCategoryName;

    /**
     * The list of skills that belong to this category.
     * It is fetched lazily, meaning it won't be fetched until it is accessed for the first time.
     * It is also ignored when the SkillCategory object is serialized to JSON.
     * The 'transient' keyword is used to indicate that this field should not be serialized.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "skillCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private transient List<Skill> skills;

}