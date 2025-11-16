package com.openclassrooms.mddapi.interfaces;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.dto.Users.UserEditRequestDto;
import com.openclassrooms.mddapi.dto.Users.UserEditResponseDto;

public interface UserServiceInterface {
    /**
     * Fetch user by their id
     * 
     * @param id id of the searched user
     * @return details from the user if they exists
     */
    UserDetailsDto getUserById(Integer id);

    /**
     * Retrieve informations from the current user
     * 
     * @return details from the current user
     */
    UserDetailsDto getUserDetails();

    /**
     * Edit current user's informations
     * 
     * @param UserEditRequestDto new informations posted by the current user to edit
     *                           their datas
     * @return an informative message
     */
    UserEditResponseDto editUser(UserEditRequestDto UserEditRequestDto);
}
