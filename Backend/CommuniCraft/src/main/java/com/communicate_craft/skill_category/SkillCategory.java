package com.communicate_craft.skill_category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public SkillCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
