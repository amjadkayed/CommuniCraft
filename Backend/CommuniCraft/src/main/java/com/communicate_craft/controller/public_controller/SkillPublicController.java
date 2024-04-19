package com.communicate_craft.controller.public_controller;

import com.communicate_craft.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/public/skill")
@RequiredArgsConstructor
public class SkillPublicController {
    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<Object> getSkillsByCategory(@RequestParam("categoryId") Long categoryId) {
        log.info("SkillPublicController --> getSkillsByCategory, categoryID: {}", categoryId);
        return ResponseEntity.ok(skillService.getSkillsByCategoryId(categoryId));
    }
}
