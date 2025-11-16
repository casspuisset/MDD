package com.openclassrooms.mddapi.dto.Auth;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;

    public AuthResponseDto(String token) {
        this.token = token;
    }
}
