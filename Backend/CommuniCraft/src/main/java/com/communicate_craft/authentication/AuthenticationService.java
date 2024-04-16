package com.communicate_craft.authentication;

import com.communicate_craft.authentication.dto.AuthenticationRequest;
import com.communicate_craft.authentication.dto.AuthenticationResponse;
import com.communicate_craft.authentication.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException;

}
