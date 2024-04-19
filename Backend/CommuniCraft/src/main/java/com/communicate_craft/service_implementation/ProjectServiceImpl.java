package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.ProjectDTO;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.Project;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.ProjectRepository;
import com.communicate_craft.service.LocationService;
import com.communicate_craft.service.ProjectService;
import com.communicate_craft.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final LocationService locationService;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty())
            throw new EntityNotFoundException("Project with id " + projectId + " is not found");
        return project.get();
    }

    @Override
    public Project addProject(ProjectDTO projectDTO, Long ownerId) {
        Project project = new Project(projectDTO);
        Optional<User> owner = userService.findByUserId(ownerId);
        if (owner.isEmpty())
            throw new EntityNotFoundException("User with id " + ownerId + " is not found");
        Optional<Location> location = locationService.findById(projectDTO.getLocationId());
        if (location.isEmpty())
            throw new EntityNotFoundException("User with id " + ownerId + " is not found");
        project.setLocation(location.get());
        project.setOwner(owner.get());
        projectRepository.save(project);
        return project;
    }
}
