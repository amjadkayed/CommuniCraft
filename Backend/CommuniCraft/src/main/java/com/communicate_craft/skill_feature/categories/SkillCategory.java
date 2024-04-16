package com.communicate_craft.skill_feature.categories;

import com.communicate_craft.skill_feature.skills.Skill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SkillCategories")
@NoArgsConstructor
@AllArgsConstructor
public class SkillCategory {
    @Id
    @GeneratedValue
    @Column(name = "CategoryId")
    private Long categoryId;

    @NotEmpty(message = "empty category name")
    @Column(name = "CategoryName")
    private String categoryName;

    @OneToMany(mappedBy = "skillCategory", orphanRemoval = true)
    @JsonIgnore
    private List<Skill> skills = new ArrayList<>();

    public SkillCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
