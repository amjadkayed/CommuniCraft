package com.communicate_craft.model;

import com.communicate_craft.dto.ProjectDTO;
import com.communicate_craft.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Projects")
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "ProjectID")
    private Long projectId;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectSkills> requiredSkills = new ArrayList<>();

    @Column(name = "CreationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "ExpectedCompletionDate")
    private LocalDateTime expectedCompletionDate;

    @NotEmpty(message = "project description can't be empty")
    @Column(name = "Description")
    private String description;

    @NotEmpty(message = "project title can't be empty")
    @Column(name = "Title")
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    @Column(name = "ImageURL")
    private String imageURL;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_location_id", nullable = false)
    private Location location;

    public Project() {
        status = Status.NOT_STARTED;
        creationDate = LocalDateTime.now();
    }

    public Project(ProjectDTO projectDTO) {
        this();
        if(projectDTO.getStatus() != null)setStatus(projectDTO.getStatus());
        setProjectId(projectDTO.getProjectId());
        setExpectedCompletionDate(projectDTO.getExpectedCompletionDate());
        setDescription(projectDTO.getDescription());
        setTitle(projectDTO.getTitle());
        setImageURL(projectDTO.getImageURL());
        setRequiredSkills(projectDTO.getRequiredSkills().stream()
                .map(dto -> new ProjectSkills(dto, this))
                .toList());
    }

    @JsonProperty
    public String getOwner() {
        return owner.getUsername();
    }

    public Long getOwnerId() {
        return owner.getUserID();
    }
}
