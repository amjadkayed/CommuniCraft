package com.communicate_craft.controller.admin_controller;

import com.communicate_craft.dto.SkillDTO;
import com.communicate_craft.service.SkillService;
import com.communicate_craft.utility.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/skill")
@RequiredArgsConstructor
public class SkillAdminController {
    private final SkillService skillService;

    @PostMapping("")
    public ResponseEntity<Object> addNewSkill(@Valid @RequestBody SkillDTO skill, BindingResult result) {
        log.info("SkillAdminController --> addNewSkill: {}", skill);
        Validator.validateBody(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.addSkill(skill));
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<Object> updateSkill(@PathVariable Long skillId, @Valid @RequestBody SkillDTO skillDTO, BindingResult result) {
        log.info("SkillAdminController --> updateSkill");
        Validator.validateBody(result);
        skillDTO.setSkillId(skillId);
        return ResponseEntity.ok(skillService.updateSkill(skillDTO));
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Object> deleteSkill(@PathVariable Long skillId) {
        log.info("SkillAdminController --> deleteSkill");
        skillService.deleteSkill(skillId);
        return ResponseEntity.ok().build();
    }
}
