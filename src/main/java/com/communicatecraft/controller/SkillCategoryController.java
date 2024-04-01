package com.communicatecraft.controller;

import com.communicatecraft.helper.Generator;
import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.service.SkillCategoryService;
import com.communicatecraft.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/skillcategories")
@RequiredArgsConstructor
public class SkillCategoryController {

    private final SkillCategoryService skillCategoryService;

    @GetMapping
    public ResponseEntity<Object> getAllSkillCategories() {
        try {
            return ResponseEntity.ok(skillCategoryService.getAllSkillCategories());
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving skill categories: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillCategory> getSkillCategoryById(@PathVariable Integer id) {
        return skillCategoryService.getSkillCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createSkillCategory(@Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        if (result.hasErrors()) {
            // Handle the validation errors
            return new ResponseEntity<>(Generator.bindingResultToErrorList(result), HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(skillCategoryService.saveSkillCategory(skillCategory), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error adding skill category: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSkillCategory(@PathVariable Integer id, @Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        skillCategory.setSkillsCategoryId(id);
        try {
            Optional<SkillCategory> updatedSkillCategory = skillCategoryService.updateSkillCategory(skillCategory);
            return updatedSkillCategory.isPresent() ? new ResponseEntity<>(updatedSkillCategory, HttpStatus.OK)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating skill category: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteSkillCategory(@PathVariable Integer id) {
        try {
            skillCategoryService.deleteSkillCategory(id);
            return ResponseEntity.ok(new ResponseMessage("Skill category with id " + id + "was deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
