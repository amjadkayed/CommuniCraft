package com.communicate_craft.authentication.dto;

import com.communicate_craft.user.User;
import lombok.Builder;

@Builder
public record AuthenticationResponse(String token, User user) {
}
