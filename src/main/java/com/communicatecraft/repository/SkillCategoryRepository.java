package com.communicatecraft.repository;
import com.communicatecraft.model.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Integer> {
    // This will automatically implement the basic CRUD operations
}
