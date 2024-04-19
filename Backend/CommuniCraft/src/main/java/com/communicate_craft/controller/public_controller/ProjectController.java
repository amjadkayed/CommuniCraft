package com.communicate_craft.controller.public_controller;

import com.communicate_craft.dto.ProjectDTO;
import com.communicate_craft.enums.Status;
import com.communicate_craft.model.Project;
import com.communicate_craft.model.User;
import com.communicate_craft.service.ProjectService;
import com.communicate_craft.utility.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/api/public/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<Object> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getAllProjects(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @GetMapping("/showcase")
    public ResponseEntity<Object> showcaseAndSharing(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<Project> projects = projectService.getShowcase(categoryId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/library")
    public ResponseEntity<Object> library(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<Project> projects = projectService.getLibrary(categoryId);
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<Object> addProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult result, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        Validator.validateBody(result);
        Project project = projectService.addProject(projectDTO, userDetails.getUserID());
        log.info("ProjectController --> project was added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Object> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectDTO projectDTO, BindingResult result, Authentication authentication) {
        projectDTO.setProjectId(projectId);
        User userDetails = (User) authentication.getPrincipal();
        Validator.validateBody(result);
        Project project = projectService.updateProject(projectDTO, userDetails.getUserID());
        log.info("ProjectController --> project was updated successfully");
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Object> deleteProject(@PathVariable Long projectId, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        projectService.deleteProject(projectId, userDetails.getUserID());
        log.info("ProjectController --> project was deleted successfully");
        return ResponseEntity.ok().build();
    }
}
