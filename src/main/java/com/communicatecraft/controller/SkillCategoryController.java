package com.communicatecraft.controller;

import com.communicatecraft.helper.Generator;
import com.communicatecraft.model.ApiResponse;
import com.communicatecraft.model.SkillCategory;
import com.communicatecraft.service.SkillCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/skillcategories")
@RequiredArgsConstructor
public class SkillCategoryController {

    private final SkillCategoryService skillCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillCategory>>> getAllSkillCategories() {
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillCategoryService.getAllSkillCategories(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skill's categories: " + e.getMessage())));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillCategory>> getSkillCategoryById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillCategoryService.getSkillCategoryById(id), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skill category with id " + id + ": " + e.getMessage())));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SkillCategory>> createSkillCategory(@Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        if (result.hasErrors()) {
            // Handle the validation errors
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, Generator.bindingResultToErrorList(result)));
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(skillCategoryService.saveSkillCategory(skillCategory), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList(e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillCategory>> updateSkillCategory(@PathVariable Integer id, @Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, Generator.bindingResultToErrorList(result)));
        }
        skillCategory.setSkillsCategoryId(id);
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillCategoryService.updateSkillCategory(skillCategory), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList(e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSkillCategory(@PathVariable Integer id) {
        try {
            skillCategoryService.deleteSkillCategory(id);
            return ResponseEntity.ok(new ApiResponse<>(true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, Collections.singletonList(e.getMessage())));
        }
    }
}
