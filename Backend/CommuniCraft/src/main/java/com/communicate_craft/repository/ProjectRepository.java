package com.communicate_craft.repository;

import com.communicate_craft.enums.Status;
import com.communicate_craft.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(Status status);

    void deleteByProjectId(Long projectId);
}
