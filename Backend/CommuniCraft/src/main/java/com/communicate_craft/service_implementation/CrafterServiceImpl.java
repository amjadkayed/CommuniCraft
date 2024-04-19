package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.model.Crafter;
import com.communicate_craft.model.Skill;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.CrafterRepository;
import com.communicate_craft.service.CrafterService;
import com.communicate_craft.service.UserService;
import com.communicate_craft.utility.Converter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrafterServiceImpl implements CrafterService {
    private final CrafterRepository crafterRepository;
    private final UserService userService;

    @Override
    public List<Crafter> getAllCrafters() {
        return crafterRepository.findAll();
    }

    @Override
    public Crafter addCrafter(Crafter crafter) {
        userService.checkUserExists(crafter.getUser());
        return crafterRepository.save(crafter);
    }

    @Override
    public Crafter getCrafterByEmail(String crafterEmail) {
        Optional<User> user = userService.findByUserEmail(crafterEmail);
        if (user.isEmpty())
            throw new EntityNotFoundException("User with email " + crafterEmail + " is not found");
        Optional<Crafter> crafter = crafterRepository.findById(user.get().getUserID());
        if (crafter.isEmpty())
            throw new EntityNotFoundException("Crafter with id " + user.get().getUserID() + " is not found");
        return crafter.get();
    }

    @Override
    public Crafter updateCrafterSkills(String crafterEmail, Set<Skill> newSkills) {
        Crafter crafter = getCrafterByEmail(crafterEmail);
        crafter.setSkills(newSkills);
        crafterRepository.save(crafter);
        return crafter;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request, Role... roles) {
        AuthenticationResponse response = userService.register(request, Role.CRAFTER);
        User user = (User) response.user();
        log.info("CrafterServiceImpl --> register --> user: " + user.getUsername() + " is a crafter");
        return new AuthenticationResponse(response.token(), addCrafter(Converter.convertUserToCrafter(user)));

    }
}
