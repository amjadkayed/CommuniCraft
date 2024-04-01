package com.communicatecraft.controller;

import com.communicatecraft.helper.Generator;
import com.communicatecraft.model.Skill;
import com.communicatecraft.service.SkillService;
import com.communicatecraft.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/skillcategories/{skillcategoryid}/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<Object> getAllSkills(@PathVariable Integer skillcategoryid) {
        try {
            return ResponseEntity.ok(skillService.getAllSkillsByCategoryId(skillcategoryid));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error retrieving skills: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSkillById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(skillService.getSkillById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error retrieving skill with id " + id + ": " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createSkill(@Valid @RequestBody Skill skill, BindingResult result, @PathVariable Integer skillcategoryid) {
        if (result.hasErrors()) {
            // Handle the validation errors
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        try {
            skill.setSkillCategoryId(skillcategoryid);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(skillService.saveSkill(skill));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error adding skill: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSkill(@PathVariable Integer id, @Valid @RequestBody Skill skill, BindingResult result, @PathVariable Integer skillcategoryid) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        skill.setSkillId(id);
        try {
            return ResponseEntity.ok(skillService.updateSkill(skill));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error updating skill with id " + id + ": " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteSkill(@PathVariable Integer id) {
        try {
            skillService.deleteSkill(id);
            return ResponseEntity.ok(new ResponseMessage("Skill with id " + id + "was deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Error deleting skill with id " + id + ": " + e.getMessage()));
        }
    }
}
