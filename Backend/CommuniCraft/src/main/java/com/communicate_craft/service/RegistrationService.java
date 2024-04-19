package com.communicate_craft.service;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;

public interface RegistrationService {
    AuthenticationResponse register(RegisterRequest request, Role... roles);

}
