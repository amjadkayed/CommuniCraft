package com.communicate_craft.controller.public_controller;

import com.communicate_craft.dto.AuthenticationRequest;
import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.service.AuthenticationService;
import com.communicate_craft.service_implementation.RegistrationServiceImpl;
import com.communicate_craft.utility.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        log.info("AuthenticationController -->registering a user with username: " + request.getUsername());
        Validator.validateBody(result);
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Authorization", "Bearer " + response.token())
                .body(response.user());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) {
        log.info("AuthenticationController --> logging a user with email: " + request.email());
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.token())
                .body(response.user());
    }
}




