package com.communicate_craft.dto;

import com.communicate_craft.enums.Role;
import com.communicate_craft.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest extends User {
    @NotNull(message = "Location ID cannot be null")
    private Integer locationId;

    public RegisterRequest(String username, String firstName, String lastName,
                           String email, String password,
                           String phoneNumber, Integer locationId, Role role) {
        setUsername(username);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        setPhoneNumber(phoneNumber);
        setRole(role);
        this.locationId = locationId;
    }
}
