package com.communicate_craft.controller.public_controller;

import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.exception.DuplicateEntryException;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.User;
import com.communicate_craft.service_implementation.LocationServiceImpl;
import com.communicate_craft.service_implementation.UserServiceImpl;
import com.communicate_craft.utility.ErrorsResponse;
import com.communicate_craft.utility.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/api/public/users")
@RequiredArgsConstructor
public class UserController {
    //todo: fix everything
    private final UserServiceImpl userServiceImpl;
    private final LocationServiceImpl locationServiceImpl;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId) {
        Optional<User> user = userServiceImpl.findByUserId(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @Valid @RequestBody RegisterRequest updateUserDTO, BindingResult result) {
        Validator.validateBody(result);
        Optional<Location> location = locationServiceImpl.findById(updateUserDTO.getLocationId());
        if (location.isEmpty())
            return new ResponseEntity<>(new ErrorsResponse("Location not found with id: " + updateUserDTO.getLocationId()), HttpStatus.BAD_REQUEST);

        // check if the user exists
        Optional<User> user = userServiceImpl.findByUserId(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        //
        User userDetails = null; //Converter.convertUserUpdateDtoToUser(updateUserDTO, user.get(), location.get());
        userDetails.setUserID(userId);
        try {
            User updatedUser = userServiceImpl.updateUser(userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity<>(new ErrorsResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        Optional<User> user = userServiceImpl.findByUserId(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userServiceImpl.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
