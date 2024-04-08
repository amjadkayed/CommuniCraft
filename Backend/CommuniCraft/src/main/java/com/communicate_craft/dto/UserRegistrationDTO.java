package com.communicate_craft.dto;

import com.communicate_craft.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserRegistrationDTO extends User {
    @NotNull(message = "Location ID cannot be null")
    private Integer locationId;

}
