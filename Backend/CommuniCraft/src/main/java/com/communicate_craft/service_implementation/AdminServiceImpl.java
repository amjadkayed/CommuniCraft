package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.UserRepository;
import com.communicate_craft.service.AdminService;
import com.communicate_craft.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository adminRepository;
    private final UserService userService;

    @Override
    public List<User> getAllAdmins() {
        log.error("AdminServiceImpl --> getAllAdmins");
        return adminRepository.findByRole(Role.ADMIN);
    }

    @Override
    public User getAdminById(Long adminId) {
        log.error("AdminServiceImpl --> getAdminById");
        Optional<User> admin = adminRepository.findById(adminId);
        if (admin.isEmpty())
            throw new EntityNotFoundException("There isn't any user with ID = " + adminId);
        if (admin.get().getRole().equals(Role.ADMIN))
            return admin.get();
        throw new IllegalArgumentException("User with ID = " + adminId + " is not an admin");
    }

    @Override
    public void deleteAdminById(Long adminId) {
        log.error("AdminServiceImpl --> deleteAdminById");
        getAdminById(adminId);
        adminRepository.deleteById(adminId);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request, Role... roles) {
        log.error("AdminServiceImpl --> register");
        return userService.register(request, Role.ADMIN);
    }
}
