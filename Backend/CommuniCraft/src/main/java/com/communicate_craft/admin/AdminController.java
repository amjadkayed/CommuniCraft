package com.communicate_craft.admin;

import com.communicate_craft.authentication.dto.AuthenticationResponse;
import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.exceprions.DuplicateEntryException;
import com.communicate_craft.location.LocationServiceImpl;
import com.communicate_craft.utils.Converter;
import com.communicate_craft.utils.ErrorsResponse;
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
@RequestMapping("/api/admin/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final LocationServiceImpl locationServiceImpl;

    @GetMapping("")
    public ResponseEntity<Object> getAllAdmins() {
        log.info("AdminUserController --> getAllAdmins");
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Object> getAdminById(@PathVariable Integer adminId) {
        log.info("AdminUserController --> getAdminById");
        try {
            return ResponseEntity.ok(adminService.getAdminById(adminId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> registerAdmin(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        log.info("AdminUserController --> registerAdmin --> registering a user with username: " + request.getUsername());
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }

        try {
            AuthenticationResponse response = adminService.registerNewAdmin(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Authorization", "Bearer " + response.token())
                    .body(response.user());
        } catch (
                EntityNotFoundException e) {
            log.error("AdminUserController --> registerAdmin --> " + e.getMessage());
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (DuplicateEntryException |
                 IllegalArgumentException e) {
            log.error("AdminUserController --> registerAdmin --> " + e.getMessage());
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable Integer adminId) {
        log.error("AdminUserController --> deleteAdmin");
        try {
            adminService.deleteAdminById(adminId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
