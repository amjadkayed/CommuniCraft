package com.communicate_craft.service;

import com.communicate_craft.model.Crafter;
import com.communicate_craft.model.Skill;

import java.util.List;
import java.util.Set;

public interface CrafterService extends RegistrationService {
    List<Crafter> getAllCrafters();

    Crafter addCrafter(Crafter crafter);

    Crafter getCrafterByEmail(String crafterEmail);

    Crafter updateCrafterSkills(String crafterEmail, Set<Skill> newSkills);
}
