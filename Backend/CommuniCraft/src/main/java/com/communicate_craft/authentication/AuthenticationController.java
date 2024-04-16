package com.communicate_craft.authentication;

import com.communicate_craft.authentication.dto.AuthenticationRequest;
import com.communicate_craft.authentication.dto.AuthenticationResponse;
import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.exceprions.DuplicateEntryException;
import com.communicate_craft.utils.Converter;
import com.communicate_craft.utils.ErrorsResponse;
import jakarta.persistence.EntityNotFoundException;
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

    private final AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        log.info("AuthenticationController --> register --> registering a user with username: " + request.getUsername());
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        try {
            AuthenticationResponse response = authenticationServiceImpl.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Authorization", "Bearer " + response.token())
                    .body(response.user());
        } catch (
                EntityNotFoundException e) {
            log.error("AuthenticationController --> register --> " + e.getMessage());
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (DuplicateEntryException |
                 IllegalArgumentException e) {
            log.error("AuthenticationController --> register --> " + e.getMessage());
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) {
        log.info("AuthenticationController --> login --> logging a user with email: " + request.email());
        try {
            AuthenticationResponse response = authenticationServiceImpl.authenticate(request);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + response.token())
                    .body(response.user());
        } catch (Exception e) {
            log.error("AuthenticationController --> login --> " + e.getMessage());
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}




