package com.communicate_craft.service;

import com.communicate_craft.dto.AuthenticationRequest;
import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException;

}
