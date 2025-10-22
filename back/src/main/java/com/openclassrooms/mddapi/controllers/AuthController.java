package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Auth.LoginRequestDto;
import com.openclassrooms.mddapi.dto.Auth.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.services.Auth.AuthenticationService;
import com.openclassrooms.mddapi.services.Auth.LoginService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginService loginService;

    private final AuthenticationService authenticationService;

    public AuthController(LoginService loginService, AuthenticationService authenticationService) {
        this.loginService = loginService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        var authResponseDto = loginService.login(loginRequestDto);

        return ResponseEntity.ok().body(authResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto user) {

        var authResponseDto = loginService.register(user);
        return ResponseEntity.ok().body(authResponseDto);

    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDto> getUserDetails() {
        var response = authenticationService.getAuthenticatedUser();
        return ResponseEntity.ok().body(response);
    }
}
