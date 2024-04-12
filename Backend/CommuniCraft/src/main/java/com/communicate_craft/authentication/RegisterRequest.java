package com.communicate_craft.authentication;

import com.communicate_craft.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RegisterRequest extends User {
    @NotNull(message = "Location ID cannot be null")
    private Integer locationId;

}
