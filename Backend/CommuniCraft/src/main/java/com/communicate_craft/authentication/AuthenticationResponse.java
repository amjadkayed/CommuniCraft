package com.communicate_craft.authentication;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
