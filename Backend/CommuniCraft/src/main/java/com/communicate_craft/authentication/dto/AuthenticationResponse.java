package com.communicate_craft.authentication.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token, Object user) {
}
