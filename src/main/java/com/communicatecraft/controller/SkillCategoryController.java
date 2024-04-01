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

/**
 * Controller class for SkillCategory.
 * This class provides RESTful endpoints for CRUD operations on SkillCategory objects.
 * The @RestController annotation is used to indicate that this class is a REST controller.
 * The @RequestMapping annotation is used to map the endpoints to the /api/skillcategories path.
 * The @RequiredArgsConstructor annotation is used to automatically generate a constructor with required arguments.
 */
@RestController
@RequestMapping("/api/skillcategories")
@RequiredArgsConstructor
public class SkillCategoryController {

    /**
     * The service that this controller will use to interact with the database.
     */
    private final SkillCategoryService skillCategoryService;

    /**
     * Retrieves all SkillCategory objects from the database.
     *
     * @return a list of all SkillCategory objects as data in an ApiResponse with error message to handle the error in
     * retrieving requested data.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillCategory>>> getAllSkillCategories() {
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillCategoryService.getAllSkillCategories(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skill's categories: " + e.getMessage())));
        }
    }

    /**
     * Retrieves a SkillCategory object by its id.
     *
     * @param id the id of the SkillCategory object to retrieve.
     * @return the SkillCategory object with the given id as data in an ApiResponse with error message if the required id is
     * not exist or if an error occurs while retrieving it.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillCategory>> getSkillCategoryById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillCategoryService.getSkillCategoryById(id), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skill category with id " + id + ": " + e.getMessage())));
        }
    }

    /**
     * Creates a new SkillCategory object in the database.
     *
     * @param skillCategory the SkillCategory object to create.
     * @return the created SkillCategory object as data in an ApiResponse with error message if skillCategory's
     * information is not valid as defined in the entity, or if saving process threw an error.
     */
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

    /**
     * Updates a SkillCategory object in the database.
     *
     * @param id            the id of the SkillCategory object to update.
     * @param skillCategory the SkillCategory object to update.
     * @return the updated SkillCategory object.
     */
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

    /**
     * Deletes a SkillCategory object from the database.
     *
     * @param id the id of the SkillCategory object to delete.
     * @return a boolean indicating whether the deletion was successful.
     */
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
