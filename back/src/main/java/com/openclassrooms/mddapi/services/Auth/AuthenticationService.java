package com.openclassrooms.mddapi.services.Auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.exceptions.UnauthorizedException;
import com.openclassrooms.mddapi.interfaces.AuthenticationServiceInterface;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService implements AuthenticationServiceInterface {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationService(UserMapper userMapper, UserRepository userRepository,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    // get user from the context
    public Optional<User> getAuthenticatedUserFromContext() {
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
                    throw new UnauthorizedException("user is not authenticated");
                });

    }

    // authenticate the user
    public Authentication tryAuthenticateUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return authentication;
        } catch (JwtException ex) {
            throw new UnauthorizedException("user is not authenticated");
        }
    }

    // Map a User in a Dto
    public UserDetailsDto mapTo(User user) {
        UserDetailsDto userDetailsDto = userMapper.mapToDto(user);
        return userDetailsDto;
    }

}