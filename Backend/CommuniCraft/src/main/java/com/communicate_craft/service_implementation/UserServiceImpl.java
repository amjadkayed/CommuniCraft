package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.exception.DuplicateEntryException;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.UserRepository;
import com.communicate_craft.service.UserService;
import com.communicate_craft.utility.Converter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final LocationServiceImpl locationServiceImpl;
    private final PasswordEncoder passwordEncoder;

    private void checkDuplicatedUserFields(User user) {
        // check if email, username, or phone number are existed before
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Email already exists: " + user.getEmail());
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateEntryException("Username already exists: " + user.getUsername());
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new DuplicateEntryException("Phone number already exists: " + user.getPhoneNumber());
        }
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request, Role... userRoles) {
        // 1- validate user's role
        // 2- validate user's location
        // 3- convert RegisterRequest into User
        // 4- encode the password
        // 5- check for duplication in phone, username, email
        // 6- save the user
        // 7- generate the token and return it with the user
        // 8- if the user is a crafter then save it
        log.info("UserServiceImpl --> registerUser --> username:" + request.getUsername());
        Optional<Location> location = locationServiceImpl.findById(request.getLocationId());
        if (location.isEmpty())
            throw new EntityNotFoundException("Location not found with id: " + request.getLocationId());
        User user = Converter.convertUserDtoToUser(request, location.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkDuplicatedUserFields(user);
        var jwtToken = jwtService.generateToken(userRepository.save(user));
        log.info("UserServiceImpl --> registerUser --> token was generated for username: " + user.getUsername());
        return new AuthenticationResponse(jwtToken, user);
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

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUserEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public void checkUserExists(User user) {
        if (user == null || findByUserId(user.getUserID()).isEmpty())
            throw new EntityNotFoundException("User is not found");
    }

}
