package com.communicate_craft.controller.admin_controller;

import com.communicate_craft.dto.SkillDTO;
import com.communicate_craft.service.SkillService;
import com.communicate_craft.utility.Converter;
import com.communicate_craft.utility.ErrorsResponse;
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
@RequestMapping("/api/admin/skill")
@RequiredArgsConstructor
public class SkillAdminController {
    private final SkillService skillService;

    @PostMapping("")
    public ResponseEntity<Object> addNewSkill(@Valid @RequestBody SkillDTO skill, BindingResult result) {
        log.info("SkillAdminController --> addNewSkill");
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(skillService.addSkill(skill));
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ErrorsResponse("Error while saving the category"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<Object> updateSkill(@PathVariable Long skillId, @Valid @RequestBody SkillDTO skillDTO, BindingResult result) {
        log.info("SkillAdminController --> updateSkill");
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        try {
            skillDTO.setSkillId(skillId);
            return ResponseEntity.ok(skillService.updateSkill(skillDTO));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Object> deleteSkill(@PathVariable Long skillId) {
        log.info("SkillAdminController --> deleteSkill");
        try {
            skillService.deleteSkill(skillId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
