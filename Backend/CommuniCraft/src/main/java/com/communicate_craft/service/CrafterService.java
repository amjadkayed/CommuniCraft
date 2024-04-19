package com.communicate_craft.service;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.model.Crafter;

import java.util.List;

public interface CrafterService  extends RegistrationService{
    List<Crafter> getAllCrafters();

    Crafter addCrafter(Crafter crafter);
}
