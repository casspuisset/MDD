package com.openclassrooms.mddapi.dto.Users;

import lombok.Data;

@Data
public class UserEditRequestDto {

    private String name;

    private String email;

    private String password;
}
