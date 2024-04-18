package com.communicate_craft.service_implementation;

import com.communicate_craft.repository.CrafterRepository;
import com.communicate_craft.service.CrafterService;
import com.communicate_craft.model.Crafter;
import com.communicate_craft.model.User;
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
    private final UserService userService;

    private void checkUserExists(User user) {
        if (user == null || userService.findByUserId(user.getUserID()).isEmpty())
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
}
