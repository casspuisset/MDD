package com.openclassrooms.mddapi.dto.Auth;

import lombok.Data;

@Data
public class AuthRequestDto {

    private String email;

    private String password;
}
