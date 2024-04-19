package com.communicate_craft.service;

import com.communicate_craft.dto.ProjectDTO;
import com.communicate_craft.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    Project getProjectById(Long projectId);

    Project addProject(ProjectDTO projectDTO, Long ownerId);
}
