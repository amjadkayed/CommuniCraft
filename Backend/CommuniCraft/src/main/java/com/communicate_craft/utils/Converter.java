package com.communicate_craft.utils;

import com.communicate_craft.dto.UserRegistrationDTO;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Converter {
    private Converter() {
        throw new IllegalStateException("Converter class");
    }

    public static Map<String, String> convertBindingResultToErrorResponse(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    public static User convertUserDtoToUser(UserRegistrationDTO registrationDTO, Location location) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setPasswordHash(registrationDTO.getPasswordHash());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setLocation(location);
        return user;
    }

    public static User convertUserUpdateDtoToUser(UserRegistrationDTO newUser, User oldUser, Location location) {
        oldUser.setUsername(newUser.getUsername());
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPasswordHash(newUser.getPasswordHash());
        oldUser.setPhoneNumber(newUser.getPhoneNumber());
        oldUser.setUserImageURL(newUser.getUserImageURL());
        oldUser.setLastOnlineTime(LocalDateTime.now());
        oldUser.setLocation(location);
        oldUser.setRating(newUser.getRating());
        oldUser.setNumberOfReviews(newUser.getNumberOfReviews());
        oldUser.setTotalSalary(newUser.getTotalSalary());
        return oldUser;
    }
}
