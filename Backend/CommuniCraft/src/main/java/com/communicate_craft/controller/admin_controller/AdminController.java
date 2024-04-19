package com.communicate_craft.controller.admin_controller;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.service.AdminService;
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
@RequestMapping("/api/admin/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("")
    public ResponseEntity<Object> getAllAdmins() {
        log.info("AdminUserController --> getAllAdmins");
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Object> getAdminById(@PathVariable Long adminId) {
        log.info("AdminUserController --> getAdminById");
        return ResponseEntity.ok(adminService.getAdminById(adminId));
    }

    @PostMapping("")
    public ResponseEntity<Object> registerAdmin(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        log.info("AdminUserController --> registering: {}", request.getUsername());
        Validator.validateBody(result);
        AuthenticationResponse response = adminService.register(request);
        log.info("AdminController --> registered: {} successfully", request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Authorization", "Bearer " + response.token())
                .body(response.user());
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable Long adminId) {
        log.info("AdminUserController --> deleting: {}", adminId);
        adminService.deleteAdminById(adminId);
        return ResponseEntity.ok().build();
    }

}
