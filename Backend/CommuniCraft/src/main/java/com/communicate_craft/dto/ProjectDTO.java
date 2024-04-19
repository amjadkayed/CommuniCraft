package com.communicate_craft.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private LocalDateTime expectedCompletionDate;

    @NotEmpty(message = "project description can't be empty")
    private String description;

    @NotEmpty(message = "project title can't be empty")
    private String title;
    @NotNull(message = "Location ID cannot be null")
    private Long locationId;
    private List<ProjectSkillsDTO> requiredSkills;
}
