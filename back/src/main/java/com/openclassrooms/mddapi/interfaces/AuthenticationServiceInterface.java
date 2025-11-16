package com.openclassrooms.mddapi.interfaces;

import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.models.User;

public interface AuthenticationServiceInterface {
    Optional<User> getAuthenticatedUserFromContext();

    UserDetailsDto getAuthenticatedUser();

    Authentication tryAuthenticateUser(String email, String password);

    UserDetailsDto mapTo(User user);
}
