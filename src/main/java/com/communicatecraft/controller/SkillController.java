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

@RestController
@RequestMapping("/api/skillcategories/{skillcategoryid}/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Skill>>> getAllSkills(@PathVariable Integer skillcategoryid){
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillService.getAllSkillsByCategoryId(skillcategoryid), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skills: " + e.getMessage())));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> getSkillById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(skillService.getSkillById(id), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, Collections.singletonList("Error retrieving skill with id " + id + ": " + e.getMessage())));
        }
    }

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
