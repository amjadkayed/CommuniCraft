package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.ProjectDTO;
import com.communicate_craft.enums.Status;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.Project;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.ProjectRepository;
import com.communicate_craft.service.LocationService;
import com.communicate_craft.service.ProjectService;
import com.communicate_craft.service.UserService;
import com.communicate_craft.utility.ProjectFilterUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final LocationService locationService;
    private static final String IS_NOT_FOUND = " is not found";

    private User validateOwner(Long ownerId, Project project) {
        Optional<User> owner = userService.findByUserId(ownerId);
        if (owner.isEmpty())
            throw new EntityNotFoundException("User with id " + ownerId + IS_NOT_FOUND);
        if (project != null && !Objects.equals(ownerId, project.getOwnerId()))
            throw new AccessDeniedException("You do not have permission to control others projects");
        return owner.get();
    }

    private Project updateProjectHandler(Project currentProject, ProjectDTO newProject, Long ownerId) {
        validateOwner(ownerId, currentProject);
        Project project = new Project(newProject);
        project.setOwner(validateOwner(ownerId, currentProject));
        Optional<Location> location = locationService.findById(newProject.getLocationId());
        if (location.isEmpty()) {
            throw new EntityNotFoundException("Location with id " + newProject.getLocationId() + IS_NOT_FOUND);
        }
        project.setLocation(location.get());
        projectRepository.save(project);
        return project;
    }

    private List<Project> getProjectsByStatus(Status status) {
        return projectRepository.findByStatus(status);
    }

    private List<Project> filterBySkillCategory(List<Project> projects, Long categoryId) {
        if (categoryId == null)
            return projects;
        return ProjectFilterUtils.filterProjectsBySkillCategory(projects, categoryId);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty())
            throw new EntityNotFoundException("Project with id " + projectId + IS_NOT_FOUND);
        return project.get();
    }

    @Override
    public Project addProject(ProjectDTO projectDTO, Long ownerId) {
        return updateProjectHandler(null, projectDTO, ownerId);
    }

    @Override
    public Project updateProject(ProjectDTO projectDTO, Long ownerId) {
        Project project = getProjectById(projectDTO.getProjectId());
        return updateProjectHandler(project, projectDTO, ownerId);
    }

    @Override
    public void deleteProject(Long projectId, Long ownerId) {
        Project project = getProjectById(projectId);
        validateOwner(ownerId, project);
        if (!project.getStatus().equals(Status.NOT_STARTED)) {
            throw new IllegalArgumentException("You can't delete a project after starting it");
        }
        projectRepository.deleteByProjectId(projectId);
    }

    @Override
    public List<Project> getShowcase(Long categoryId) {
        List<Project> projects = getProjectsByStatus(Status.COMPLETED);
        projects = filterBySkillCategory(projects, categoryId);
        return projects;
    }

    @Override
    public List<Project> getLibrary(Long categoryId) {
        List<Project> projects = getProjectsByStatus(Status.NOT_STARTED);
        projects = filterBySkillCategory(projects, categoryId);
        return projects;
    }
}
