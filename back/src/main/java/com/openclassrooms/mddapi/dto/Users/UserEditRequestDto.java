package com.openclassrooms.mddapi.dto.Users;

import lombok.Data;

@Data
public class UserEditRequestDto {

    private String username;

    private String email;

    private String password;
}
