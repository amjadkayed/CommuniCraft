package com.communicate_craft.service_implementation;

import com.communicate_craft.dto.AuthenticationRequest;
import com.communicate_craft.dto.AuthenticationResponse;
import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.enums.Role;
import com.communicate_craft.model.User;
import com.communicate_craft.repository.UserRepository;
import com.communicate_craft.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RegistrationServiceImpl registrationService;

    @Override
    public AuthenticationResponse register(RegisterRequest request, Role... roles) {
        log.info("AuthenticationServiceImpl --> registering a user with email: " + request.getEmail());
        return registrationService.register(request);
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
        return new AuthenticationResponse(jwtToken, user.get());
    }
}
