package com.openclassrooms.mddapi.services.Auth;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Auth.AuthResponseDto;
import com.openclassrooms.mddapi.dto.Auth.LoginRequestDto;
import com.openclassrooms.mddapi.dto.Auth.RegisterRequestDto;
import com.openclassrooms.mddapi.exceptions.UnauthorizedException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private final AuthenticationService authenticationService;

    public LoginService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    // log an user if the email and the password are correct
    public AuthResponseDto login(final LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationService.tryAuthenticateUser(loginRequestDto.getEmail(),
                    loginRequestDto.getPassword());
            log.info(authentication.getName());
            String token = jwtService.generateToken(authentication);
            return new AuthResponseDto(token);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new UnauthorizedException("echec de l'authentification de l'utilisateur");
        }

    }

    // create a new user in the database
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        Optional<User> optionnalUser = userRepository.findByEmail(registerRequestDto.getEmail());
        if (optionnalUser.isPresent()) {

            log.warn("User already exists");
            return null;

        } else {
            String passwordEncoded = passwordEncoder.encode(registerRequestDto.getPassword());
            User user = new User();
            user.setEmail(registerRequestDto.getEmail());
            user.setPassword(passwordEncoded);
            user.setName(registerRequestDto.getName());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            Authentication authentication = authenticationService.tryAuthenticateUser(registerRequestDto.getEmail(),
                    registerRequestDto.getPassword());
            String token = jwtService.generateToken(authentication);

            return new AuthResponseDto(token);
        }
    }

}
