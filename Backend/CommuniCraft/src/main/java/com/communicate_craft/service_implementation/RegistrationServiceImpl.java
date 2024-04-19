package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.service.CrafterService;
import com.communicate_craft.service.RegistrationService;
import com.communicate_craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class RegistrationServiceImpl {

    private final Map<Role, RegistrationService> registrationStrategies;

    @Autowired
    public RegistrationServiceImpl(CrafterService crafterService, UserService userService) {
        this.registrationStrategies = new EnumMap<>(Role.class);
        this.registrationStrategies.put(Role.CRAFTER, crafterService);
        this.registrationStrategies.put(Role.CLIENT, userService);
    }

    public AuthenticationResponse register(RegisterRequest request){
        Role role = request.getRole();
        if (!registrationStrategies.containsKey(role)) {
            throw new IllegalArgumentException("Unsupported role: " + role);
        }
        return registrationStrategies.get(role).register(request, role);
    }
}
