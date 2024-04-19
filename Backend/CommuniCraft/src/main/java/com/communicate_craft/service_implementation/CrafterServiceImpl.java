package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.model.Crafter;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.CrafterRepository;
import com.communicate_craft.service.CrafterService;
import com.communicate_craft.service.UserService;
import com.communicate_craft.utility.Converter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrafterServiceImpl implements CrafterService {
    private final CrafterRepository crafterRepository;
    private final UserServiceImpl userServiceImpl;
    private final UserService userService;

    private void checkUserExists(User user) {
        if (user == null || userServiceImpl.findByUserId(user.getUserID()).isEmpty())
            throw new EntityNotFoundException("User is not found");
    }

    @Override
    public List<Crafter> getAllCrafters() {
        return crafterRepository.findAll();
    }

    @Override
    public Crafter addCrafter(Crafter crafter) {
        checkUserExists(crafter.getUser());
        return crafterRepository.save(crafter);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request, Role... roles) {
        AuthenticationResponse response = userService.register(request, Role.CRAFTER);
        User user = (User) response.user();
        log.info("CrafterServiceImpl --> register --> user: " + user.getUsername() + " is a crafter");
        return new AuthenticationResponse(response.token(), addCrafter(Converter.convertUserToCrafter(user)));

    }
}
