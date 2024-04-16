package com.communicate_craft.utils;

import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validation {

    public static void validateUserRegistrationRole(RegisterRequest request, Role... roles) {
        for (Role role : roles) {
            if (request.getRole().equals(role))
                return;
        }
        throw new IllegalArgumentException("Invalid role");
    }
}
