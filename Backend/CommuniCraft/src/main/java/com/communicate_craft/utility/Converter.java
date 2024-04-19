package com.communicate_craft.utility;

import com.communicate_craft.dto.RegisterRequest;
import com.communicate_craft.model.Crafter;
import com.communicate_craft.model.Location;
import com.communicate_craft.model.SkillCategory;
import com.communicate_craft.model.Skill;
import com.communicate_craft.dto.SkillDTO;
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

    public static User convertUserDtoToUser(RegisterRequest registrationDTO, Location location) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setLocation(location);
        user.setRole(registrationDTO.getRole());
        return user;
    }

    public static Skill convertSkillDtoToSkill(SkillDTO skillDTO, SkillCategory category) {
        Skill skill = new Skill();
        skill.setSkillId(skillDTO.getSkillId());
        skill.setSkillCategory(category);
        skill.setSkillName(skillDTO.getSkillName());
        return skill;
    }

    public static Crafter convertUserToCrafter(User user) {
        if (user == null)
            throw new IllegalArgumentException("User is required");
        return new Crafter(user.getUserID(), "", true, null, user, null );
    }

    public static User convertUserUpdateDtoToUser(RegisterRequest newUser, User oldUser, Location location) {
        oldUser.setUsername(newUser.getUsername());
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPassword(newUser.getPassword());
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
