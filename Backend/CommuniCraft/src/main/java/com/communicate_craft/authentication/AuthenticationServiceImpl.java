package com.communicate_craft.authentication;

import com.communicate_craft.authentication.dto.AuthenticationRequest;
import com.communicate_craft.authentication.dto.AuthenticationResponse;
import com.communicate_craft.authentication.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.exceprions.DuplicateEntryException;
import com.communicate_craft.location.Location;
import com.communicate_craft.location.LocationServiceImpl;
import com.communicate_craft.user.User;
import com.communicate_craft.user.UserRepository;
import com.communicate_craft.utils.Converter;
import com.communicate_craft.utils.Validation;
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
        // 6- generate the token and return it with the user
        log.error("AuthenticationServiceImpl --> registerUser --> username:" + request.getUsername());
        Validation.validateUserRegistrationRole(request, userRoles);
        Optional<Location> location = locationServiceImpl.findById(request.getLocationId());
        if (location.isEmpty())
            throw new EntityNotFoundException("Location not found with id: " + request.getLocationId());
        User user = Converter.convertUserDtoToUser(request, location.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkDuplicatedUserFields(user);
        var jwtToken = jwtService.generateToken(userRepository.save(user));
        log.info("AuthenticationServiceImpl --> registerUser --> token was generated for username: " + user.getUsername());
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("AuthenticationServiceImpl --> register --> registering a user with email: " + request.getEmail());
        return registerUser(request, Role.CLIENT, Role.CRAFTER, Role.ADMIN);
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
