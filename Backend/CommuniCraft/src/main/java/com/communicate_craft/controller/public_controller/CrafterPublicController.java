package com.communicate_craft.controller.public_controller;

import com.communicate_craft.model.Crafter;
import com.communicate_craft.model.Skill;
import com.communicate_craft.model.User;
import com.communicate_craft.service.CrafterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/public/crafter")
@RequiredArgsConstructor
public class CrafterPublicController {
    private final CrafterService crafterService;

    @GetMapping
    public ResponseEntity<Object> getAllCrafters() {
        log.info("CrafterPublicController --> getAllCrafters");
        return ResponseEntity.ok(crafterService.getAllCrafters());
    }

    @PutMapping("/{crafterId}/skills")
    public ResponseEntity<Object> updateSkills(@PathVariable Long crafterId, @RequestBody Set<Skill> newSkills, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        if(!Objects.equals(crafterId, userDetails.getUserID()))
            throw new AccessDeniedException("You do not have permission to update others skills");
        Crafter crafter = crafterService.updateCrafterSkills(userDetails.getEmail(), newSkills);
        return ResponseEntity.ok(crafter);
    }
}
