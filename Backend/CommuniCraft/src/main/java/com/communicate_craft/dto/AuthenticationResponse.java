package com.communicate_craft.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
