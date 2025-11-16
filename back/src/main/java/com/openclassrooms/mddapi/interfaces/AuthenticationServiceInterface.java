package com.openclassrooms.mddapi.interfaces;

import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.models.User;

public interface AuthenticationServiceInterface {
    /**
     * Verify if an user is connected by an existing token
     * 
     * @return the authenticate user or nothing if there is no connected user
     */
    Optional<User> getAuthenticatedUserFromContext();

    /**
     * Retrieve datas from the authenticate user
     * 
     * @return datas from the user
     */
    UserDetailsDto getAuthenticatedUser();

    /**
     * Verify if the match email/password correspond to a database's user to connect
     * them
     * 
     * @param email    email from the user request
     * @param password password from the user request
     * @return an authentication if password and email correspond
     */
    Authentication tryAuthenticateUser(String email, String password);

    UserDetailsDto mapTo(User user);
}
