package com.openclassrooms.mddapi.dto.Auth;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String email;

    private String password;
}
