package com.communicate_craft.skill_feature.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/public/skill-categories")
@RequiredArgsConstructor
public class SkillCategoryPublicController {
    private final SkillCategoryService skillCategoryService;

    @GetMapping("")
    public ResponseEntity<Object> getAllCategories() {
        log.info("SkillCategoryPublicController --> getAllCategories");
        return ResponseEntity.ok(skillCategoryService.getAllCategories());
    }
}
