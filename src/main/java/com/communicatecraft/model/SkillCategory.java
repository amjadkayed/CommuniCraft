package com.communicatecraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "SkillCategory")
public class SkillCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillsCategoryId;

    @Size(max = 128, message = "Skill category name must be less than 129 characters")
    @NotNull(message = "null category name")
    @NotBlank(message = "blank category name")
    @NotEmpty(message = "empty category name")
    @Column(length = 128, nullable = false, unique = true)
    private String skillsCategoryName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "skillCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Skill> skills;

}
