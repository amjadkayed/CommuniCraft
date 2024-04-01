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
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error retrieving skill categories: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSkillCategoryById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(skillCategoryService.getSkillCategoryById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error retrieving skill category with id " + id + ": " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createSkillCategory(@Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        if (result.hasErrors()) {
            // Handle the validation errors
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(skillCategoryService.saveSkillCategory(skillCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error adding skill category: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSkillCategory(@PathVariable Integer id, @Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        skillCategory.setSkillsCategoryId(id);
        try {
            return ResponseEntity.ok(skillCategoryService.updateSkillCategory(skillCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error updating skill category with id " + id + ": " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteSkillCategory(@PathVariable Integer id) {
        try {
            skillCategoryService.deleteSkillCategory(id);
            return ResponseEntity.ok(new ResponseMessage("Skill category with id " + id + "was deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error deleting skill category with id " + id + ": " + e.getMessage()));
        }
    }
}
