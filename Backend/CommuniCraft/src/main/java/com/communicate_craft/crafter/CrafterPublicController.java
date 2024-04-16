package com.communicate_craft.crafter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
