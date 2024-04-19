package com.communicate_craft.service;

import com.communicate_craft.model.User;

import java.util.Optional;

public interface UserService extends RegistrationService {
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUserEmail(String userEmail);

    void checkUserExists(User user);
}
