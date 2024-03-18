package com.communicatecraft.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SkillCategory")
public class SkillCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillsCategoryId;
    private String skillsCategoryName;

}
