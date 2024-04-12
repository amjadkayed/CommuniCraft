package com.communicate_craft.service;

import com.communicate_craft.dto.AuthenticationRequest;
import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.exceprions.DuplicateEntryException;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.UserRepository;
import com.communicate_craft.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final LocationService locationService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<Location> location = locationService.findById(request.getLocationId());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = Converter.convertUserDtoToUser(request, location.get());
        user.setRole(Role.CLIENT);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Email already exists: " + user.getEmail());
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateEntryException("Username already exists: " + user.getUsername());
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new DuplicateEntryException("Phone number already exists: " + user.getPhoneNumber());
        }
        user = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
