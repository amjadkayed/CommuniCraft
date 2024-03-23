package com.communicatecraft.controller;

import com.communicatecraft.helper.Generator;
import com.communicatecraft.model.ApiResponse;
import com.communicatecraft.model.Skill;
import com.communicatecraft.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controller class for Skill.
 * This class provides RESTful endpoints for CRUD operations on Skill objects.
 * The @RestController annotation is used to indicate that this class is a REST controller.
 * The @RequestMapping annotation is used to map the endpoints to the /api/skillcategories/{skillcategoryid}/skills path.
 * The @RequiredArgsConstructor annotation is used to automatically generate a constructor with required arguments.
 */
@RestController
@RequestMapping("/api/skillcategories/{skillcategoryid}/skills")
@RequiredArgsConstructor
public class SkillController {
    /**
     * The service that this controller will use to interact with the database.
     */
    private final SkillService skillService;

    /**
     * Retrieves all Skill objects associated with a specific category from the database.
     * @param skillcategoryid the id of the category to retrieve the skills for.
     * @return a list of all Skill objects associated with the specified category.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Skill>>> getAllSkills(@PathVariable Integer skillcategoryid){
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillService.getAllSkillsByCategoryId(skillcategoryid), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skills: " + e.getMessage())));
        }
    }

    /**
     * Retrieves a Skill object by its id.
     * @param id the id of the Skill object to retrieve.
     * @return the Skill object with the given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> getSkillById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillService.getSkillById(id), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skill with id " + id + ": " + e.getMessage())));
        }
    }

    /**
     * Saves a Skill object to the database.
     * @param skill the Skill object to save.
     * @return the saved Skill object.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Skill>> createSkill(@Valid @RequestBody Skill skill, BindingResult result, @PathVariable Integer skillcategoryid) {
        if (result.hasErrors()) {
            // Handle the validation errors
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, Generator.bindingResultToErrorList(result)));
        }
        try {
            skill.setSkillCategoryId(skillcategoryid);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(skillService.saveSkill(skill), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList(e.getMessage())));
        }
    }

    /**
     * Updates a Skill object in the database.
     * @param id the id of the Skill object to update.
     * @param skill the Skill object to update.
     * @return the updated Skill object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> updateSkill(@PathVariable Integer id, @Valid @RequestBody Skill skill, BindingResult result, @PathVariable Integer skillcategoryid) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, Generator.bindingResultToErrorList(result)));
        }
        skill.setSkillId(id);
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillService.updateSkill(skill), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList(e.getMessage())));
        }
    }

    /**
     * Deletes a Skill object from the database.
     * @param id the id of the Skill object to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSkill(@PathVariable Integer id) {
        try {
            skillService.deleteSkill(id);
            return ResponseEntity.ok(new ApiResponse<>(true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, Collections.singletonList(e.getMessage())));
        }
    }
}
