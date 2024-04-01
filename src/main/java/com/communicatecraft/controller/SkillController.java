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

import java.util.Optional;


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
            return new ResponseEntity<>("Error retrieving skills: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Integer id) {
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createSkill(@Valid @RequestBody Skill skill, BindingResult result, @PathVariable Integer skillcategoryid) {
        if (result.hasErrors()) {
            // Handle the validation errors
            return new ResponseEntity<>(Generator.bindingResultToErrorList(result), HttpStatus.BAD_REQUEST);
        }
        try {
            skill.setSkillCategoryId(skillcategoryid);
            return new ResponseEntity<>(skillService.saveSkill(skill), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error adding skill: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSkill(@PathVariable Integer id, @Valid @RequestBody Skill skill, BindingResult result, @PathVariable Integer skillcategoryid) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Generator.bindingResultToErrorList(result));
        }
        skill.setSkillId(id);
        try {
            Optional<Skill> updatedSkill = skillService.updateSkill(skill);
            return updatedSkill.isPresent() ? new ResponseEntity<>(updatedSkill, HttpStatus.OK)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error updating skill: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteSkill(@PathVariable Integer id) {
        try {
            skillService.deleteSkill(id);
            return ResponseEntity.ok(new ResponseMessage("Skill with id " + id + "was deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
