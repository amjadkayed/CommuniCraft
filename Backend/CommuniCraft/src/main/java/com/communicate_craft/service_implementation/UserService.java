package com.communicate_craft.service_implementation;

import com.communicate_craft.exception.DuplicateEntryException;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User saveUser(User user) throws DuplicateEntryException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Email already exists: " + user.getEmail());
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateEntryException("Username already exists: " + user.getUsername());
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new DuplicateEntryException("Phone number already exists: " + user.getPhoneNumber());
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> testUser = userRepository.findByEmail(user.getEmail());
        if (testUser.isPresent() && !testUser.get().getUserID().equals(user.getUserID())) {
            throw new DuplicateEntryException("Email already exists: " + user.getEmail());
        }
        testUser = userRepository.findByUsername(user.getUsername());
        if (testUser.isPresent() && !testUser.get().getUsername().equals(user.getUsername())) {
            throw new DuplicateEntryException("Username already exists: " + user.getUsername());
        }
        testUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (testUser.isPresent() && !testUser.get().getPhoneNumber().equals(user.getPhoneNumber())) {
            throw new DuplicateEntryException("Phone number already exists: " + user.getPhoneNumber());
        }
        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> findByUserId(Integer userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
