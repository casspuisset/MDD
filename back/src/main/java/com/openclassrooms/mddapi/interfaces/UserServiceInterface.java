package com.openclassrooms.mddapi.interfaces;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.dto.Users.UserEditRequestDto;
import com.openclassrooms.mddapi.dto.Users.UserEditResponseDto;

public interface UserServiceInterface {
    UserDetailsDto getUserById(Integer id);

    UserDetailsDto getUserDetails();

    UserEditResponseDto editUser(UserEditRequestDto UserEditRequestDto);
}
