package com.communicate_craft.controller.admin_controller;

import com.communicate_craft.model.SkillCategory;
import com.communicate_craft.service.SkillCategoryService;
import com.communicate_craft.utility.ErrorsResponse;
import com.communicate_craft.utility.Validator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/skill-categories")
@RequiredArgsConstructor
public class SkillCategoryAdminController {
    private final SkillCategoryService skillCategoryService;

    @PostMapping("")
    public ResponseEntity<Object> addNewCategory(@Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        log.info("SkillCategoryAdminController --> Adding skill category");
        Validator.validateBody(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(skillCategoryService.addCategory(skillCategory));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        log.info("SkillCategoryAdminController --> updateCategory");
        Validator.validateBody(result);
        skillCategory.setCategoryId(categoryId);
        return ResponseEntity.ok(skillCategoryService.updateCategory(skillCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
        log.info("SkillCategoryAdminController --> deleteCategory");
        skillCategoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
