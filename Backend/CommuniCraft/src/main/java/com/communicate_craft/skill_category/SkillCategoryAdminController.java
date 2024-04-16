package com.communicate_craft.skill_category;

import com.communicate_craft.utils.Converter;
import com.communicate_craft.utils.ErrorsResponse;
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
        log.info("SkillCategoryAdminController --> addNewCategory");
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(skillCategoryService.addCategory(skillCategory));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorsResponse("Error while saving the category"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody SkillCategory skillCategory, BindingResult result) {
        log.info("SkillCategoryAdminController --> updateCategory");
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        try {
            skillCategory.setCategoryId(categoryId);
            return ResponseEntity.ok(skillCategoryService.updateCategory(skillCategory));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
        log.info("SkillCategoryAdminController --> deleteCategory");
        try {
            skillCategoryService.deleteCategory(categoryId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
