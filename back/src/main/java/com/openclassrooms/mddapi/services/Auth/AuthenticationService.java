package com.openclassrooms.mddapi.services.Auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {

    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;

    }

    // get user from the context
    private Optional<User> getAuthenticatedUserFromContext() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return Optional.of(authentication)
                    .map(Authentication::getName)
                    .flatMap(userRepository::findByEmail);
        } catch (Exception ex) {
            log.error("### GET AUTHENTICATED USER ### No authenticated user found in context");
            return Optional.empty();
        }

    }

    public UserDetailsDto getAuthenticatedUser() {
        return getAuthenticatedUserFromContext()
                .map(this::mapTo)
                .orElseThrow(() -> {
                    throw null;
                });

    }

    // authenticate the user
    public Authentication tryAuthenticateUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.info("authenticated here");
            return authentication;
        } catch (JwtException ex) {
            log.error("Unable to authenticate user");
            return null;
        }
    }

    // Map a User in a Dto
    private UserDetailsDto mapTo(User user) {
        var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setName(user.getName());
        userDetailsDto.setEmail(user.getEmail());
        userDetailsDto.setId(user.getId());
        userDetailsDto.setTopics(user.getTopics());
        userDetailsDto.setCreated_at(user.getCreatedAt());
        userDetailsDto.setUpdated_at(user.getUpdatedAt());
        return userDetailsDto;
    }

}