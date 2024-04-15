package com.communicate_craft.authentication.dto;

import com.communicate_craft.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequest extends User {
    @NotNull(message = "Location ID cannot be null")
    private Integer locationId;

}
