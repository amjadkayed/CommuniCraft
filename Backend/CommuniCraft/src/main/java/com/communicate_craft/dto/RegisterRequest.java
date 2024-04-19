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
    private Long locationId;

    public RegisterRequest(UserPersonalInfo userPersonalInfo, Long locationId, Role role) {
        extractUserPersonalInfo(userPersonalInfo);
        setRole(role);
        this.locationId = locationId;
    }

    private void extractUserPersonalInfo(UserPersonalInfo userPersonalInfo) {
        setUsername(userPersonalInfo.username());
        setFirstName(userPersonalInfo.firstName());
        setLastName(userPersonalInfo.lastName());
        setEmail(userPersonalInfo.email());
        setPassword(userPersonalInfo.password());
        setPhoneNumber(userPersonalInfo.phoneNumber());
    }
}
