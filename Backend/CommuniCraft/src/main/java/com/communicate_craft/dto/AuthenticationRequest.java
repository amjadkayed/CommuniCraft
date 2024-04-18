package com.communicate_craft.dto;

import lombok.Builder;

@Builder
public record AuthenticationRequest (String email, String password){
}
