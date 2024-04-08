package com.communicate_craft.controller;

import com.communicate_craft.dto.UserRegistrationDTO;
import com.communicate_craft.enums.UserType;
import com.communicate_craft.exceprions.DuplicateEntryException;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.User;
import com.communicate_craft.service.LocationService;
import com.communicate_craft.service.UserService;
import com.communicate_craft.utils.Converter;
import com.communicate_craft.utils.ErrorsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LocationService locationService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer userId) {
        Optional<User> user = userService.findByUserId(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegistrationDTO registrationDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        Optional<Location> location = locationService.findById(registrationDTO.getLocationId());
        if (location.isEmpty())
            return new ResponseEntity<>(new ErrorsResponse("Location not found with id: " + registrationDTO.getLocationId()), HttpStatus.BAD_REQUEST);
        User user = Converter.convertUserDtoToUser(registrationDTO, location.get());
        user.setUserType(UserType.CLIENT);
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.saveUser(user));
        } catch (DuplicateEntryException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer userId, @Valid @RequestBody UserRegistrationDTO updateUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(Converter.convertBindingResultToErrorResponse(result), HttpStatus.BAD_REQUEST);
        }
        Optional<Location> location = locationService.findById(updateUserDTO.getLocationId());
        if (location.isEmpty())
            return new ResponseEntity<>(new ErrorsResponse("Location not found with id: " + updateUserDTO.getLocationId()), HttpStatus.BAD_REQUEST);

        // check if the user exists
        Optional<User> user = userService.findByUserId(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //
        User userDetails = Converter.convertUserUpdateDtoToUser(updateUserDTO, user.get(), location.get());
        userDetails.setUserID(userId);
        try {
            User updatedUser = userService.updateUser(userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        Optional<User> user = userService.findByUserId(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
