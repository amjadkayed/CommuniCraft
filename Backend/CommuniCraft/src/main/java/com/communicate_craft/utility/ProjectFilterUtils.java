package com.communicate_craft.utility;

import com.communicate_craft.model.Project;

import java.util.List;

public class ProjectFilterUtils {
    private ProjectFilterUtils() {
        throw new IllegalStateException("You can't create an object from this class");
    }

    public static List<Project> filterProjectsBySkillCategory(List<Project> projects, Long categoryId) {
        return projects.stream()
                .filter(project -> project.getRequiredSkills().stream()
                        .anyMatch(ps -> ps.getSkill().getSkillCategory().getCategoryId().equals(categoryId)))
                .toList();
    }
}
