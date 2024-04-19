package com.communicate_craft.controller.public_controller;

import com.communicate_craft.dto.ProjectDTO;
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

    @PostMapping
    public ResponseEntity<Object> addProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult result, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        System.out.println(userDetails);
        Validator.validateBody(result);
        Project project = projectService.addProject(projectDTO, userDetails.getUserID());
        log.info("ProjectController --> project was added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
}
