package com.openclassrooms.mddapi.services.Auth;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Auth.AuthResponseDto;
import com.openclassrooms.mddapi.dto.Auth.AuthRequestDto;
import com.openclassrooms.mddapi.dto.Auth.RegisterRequestDto;
import com.openclassrooms.mddapi.exceptions.UnauthorizedException;
import com.openclassrooms.mddapi.exceptions.UserAlreadyExistsException;
import com.openclassrooms.mddapi.interfaces.AuthenticationServiceInterface;
import com.openclassrooms.mddapi.interfaces.JwtServiceInterface;
import com.openclassrooms.mddapi.interfaces.LoginServiceInterface;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginService implements LoginServiceInterface {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtServiceInterface jwtService;
    private final AuthenticationServiceInterface authenticationService;

    public LoginService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
            JwtServiceInterface jwtService,
            AuthenticationServiceInterface authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    public AuthResponseDto login(final AuthRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            String email = "";
            if (loginRequestDto.getEmail().contains("@")) {
                email = loginRequestDto.getEmail();
            } else {
                email = userRepository.findByName(loginRequestDto.getEmail()).get().getEmail();
            }
            Authentication authentication = authenticationService.tryAuthenticateUser(email,
                    loginRequestDto.getPassword());
            log.info(authentication.getName());
            String token = jwtService.generateToken(authentication);
            jwtService.addAuthCookie(token, response);
            return new AuthResponseDto(token);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new UnauthorizedException("echec de l'authentification de l'utilisateur");
        }

    }

    public AuthResponseDto register(RegisterRequestDto registerRequestDto, HttpServletResponse response) {
        Optional<User> optionnalUser = userRepository.findByEmail(registerRequestDto.getEmail());
        if (optionnalUser.isPresent()) {

            log.warn("User already exists");
            throw new UserAlreadyExistsException("l'utilisateur existe déjà");

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
            jwtService.addAuthCookie(token, response);

            return new AuthResponseDto(token);
        }
    }

    public ResponseEntity<Void> logout(HttpServletResponse response) {
        jwtService.deleteAuthCookie(response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
