package com.communicatecraft.controller;
import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.service.SkillCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skillcategories")
@RequiredArgsConstructor
public class SkillCategoryController {

    private final SkillCategoryService service;

    @GetMapping
    public List<SkillCategory> getAllSkillCategories() {
        return service.getAllSkillCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillCategory> getSkillCategoryById(@PathVariable Integer id) {
        return service.getSkillCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SkillCategory createSkillCategory(@RequestBody SkillCategory skillCategory) {
        return service.saveOrUpdateSkillCategory(skillCategory);
    }

    @PutMapping("/{id}")
    public SkillCategory updateSkillCategory(@PathVariable Integer id, @RequestBody SkillCategory skillCategory) {
        skillCategory.setSkillsCategoryId(id);
        return service.saveOrUpdateSkillCategory(skillCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteSkillCategory(@PathVariable Integer id) {
        service.deleteSkillCategory(id);
    }
}
