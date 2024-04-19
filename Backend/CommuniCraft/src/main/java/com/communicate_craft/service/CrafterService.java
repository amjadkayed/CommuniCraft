package com.communicate_craft.service;

import com.communicate_craft.model.Crafter;

import java.util.List;

public interface CrafterService extends RegistrationService {
    List<Crafter> getAllCrafters();

    Crafter addCrafter(Crafter crafter);
}
