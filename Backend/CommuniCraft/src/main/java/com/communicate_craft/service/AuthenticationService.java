package com.communicate_craft.service;

import com.communicate_craft.dto.AuthenticationRequest;
import com.communicate_craft.dto.AuthenticationResponse;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService extends RegistrationService{
    AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException;
}
