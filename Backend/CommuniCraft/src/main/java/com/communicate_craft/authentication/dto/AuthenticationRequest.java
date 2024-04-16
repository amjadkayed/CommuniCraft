package com.communicate_craft.authentication.dto;

import lombok.Builder;

@Builder
public record AuthenticationRequest (String email, String password){
}
