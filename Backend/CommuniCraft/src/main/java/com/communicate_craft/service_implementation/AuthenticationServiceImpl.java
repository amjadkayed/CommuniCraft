package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.AuthenticationRequest;
import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.service.CrafterService;
import com.communicate_craft.enums.Role;
import com.communicate_craft.exception.DuplicateEntryException;
import com.communicate_craft.model.Location;
import com.communicate_craft.service.AuthenticationService;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.UserRepository;
import com.communicate_craft.utility.Converter;
import com.communicate_craft.utility.Validation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final LocationServiceImpl locationServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CrafterService crafterService;

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

    public AuthenticationResponse registerUser(RegisterRequest request, Role... userRoles) {
        // 1- validate user's role
        // 2- validate user's location
        // 3- convert RegisterRequest into User
        // 4- encode the password
        // 5- check for duplication in phone, username, email
        // 6- save the user
        // 7- generate the token and return it with the user
        // 8- if the user is a crafter then save it
        log.error("AuthenticationServiceImpl --> registerUser --> username:" + request.getUsername());
        Validation.validateUserRegistrationRole(request, userRoles);
        Optional<Location> location = locationServiceImpl.findById(request.getLocationId());
        if (location.isEmpty())
            throw new EntityNotFoundException("Location not found with id: " + request.getLocationId());
        User user = Converter.convertUserDtoToUser(request, location.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkDuplicatedUserFields(user);
        var jwtToken = jwtService.generateToken(userRepository.save(user));
        Object userToReturn = user;
        if (user.getRole().equals(Role.CRAFTER)) {
            log.info("AuthenticationServiceImpl --> registerUser --> user: " + user.getUsername() + " is a crafter");
            userToReturn = crafterService.addCrafter(Converter.convertUserToCrafter(user));
        }
        log.info("AuthenticationServiceImpl --> registerUser --> token was generated for username: " + user.getUsername());
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user(userToReturn)
                .build();
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("AuthenticationServiceImpl --> register --> registering a user with email: " + request.getEmail());
        return registerUser(request, Role.CLIENT, Role.CRAFTER);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        log.info("AuthenticationServiceImpl --> authenticate --> authenticating a user with email: " + request.email());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        Optional<User> user = userRepository.findByEmail(request.email());
        if (user.isEmpty()) {
            String msg = "User with email: " + request.email() + " is not found";
            log.info(msg);
            throw new EntityNotFoundException(msg);
        }
        log.info("User with email " + request.email() + " is found");
        var jwtToken = jwtService.generateToken(user.get());
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user(user.get())
                .build();
    }
}
