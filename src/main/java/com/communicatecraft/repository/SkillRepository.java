package com.communicatecraft.repository;

import com.communicatecraft.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    // This will automatically implement the basic CRUD operations
    List<Skill> getAllSkillsBySkillCategoryId(Integer skillCategoryId);
}
