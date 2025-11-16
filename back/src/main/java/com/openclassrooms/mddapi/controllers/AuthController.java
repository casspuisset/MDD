package com.openclassrooms.mddapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Auth.AuthRequestDto;
import com.openclassrooms.mddapi.dto.Auth.RegisterRequestDto;
import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.interfaces.AuthenticationServiceInterface;
import com.openclassrooms.mddapi.interfaces.JwtServiceInterface;
import com.openclassrooms.mddapi.interfaces.LoginServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@Tag(name = "Authentification")
public class AuthController {

        private final LoginServiceInterface loginService;

        private final JwtServiceInterface jwtService;

        private final AuthenticationServiceInterface authenticationService;

        public AuthController(JwtServiceInterface jwtService, LoginServiceInterface loginService,
                        AuthenticationServiceInterface authenticationService) {
                this.loginService = loginService;
                this.authenticationService = authenticationService;
                this.jwtService = jwtService;
        }

        @Operation(description = "Log an already registered user", responses = {
                        @ApiResponse(description = "Login successfull and authentification token generated", responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"token\":\"jwt\"}")) }),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"error\"}"))) })
        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody AuthRequestDto loginRequestDto, HttpServletResponse response) {
                var authResponseDto = loginService.login(loginRequestDto, response);

                return ResponseEntity.ok().body(authResponseDto);
        }

        @Operation(description = "Register a new user", responses = {
                        @ApiResponse(description = "Token generated", responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"token\":\"jwt\"}")) }),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)
        })
        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto user, HttpServletResponse response) {

                var authResponseDto = loginService.register(user, response);
                return ResponseEntity.ok().body(authResponseDto);

        }

        @Operation(description = "Unlog a user", responses = {
                        @ApiResponse(description = "User unloged", responseCode = "200", content = {}) })
        @PostMapping("/logout")
        public ResponseEntity<?> logoutUser(HttpServletResponse response) {
                jwtService.deleteAuthCookie(response);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Operation(description = "Get informations of the current user", responses = {
                        @ApiResponse(description = "Successfully retrieved the informations of the user", responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Test TEST\",\"email\":\"test@test.com\",\"created_at\":\"2022/02/02\",\"updated_at\":\"2022/08/02\"}")) }),
                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)
        })
        @GetMapping("/me")
        public ResponseEntity<UserDetailsDto> getUserDetails() {
                var response = authenticationService.getAuthenticatedUser();
                return ResponseEntity.ok().body(response);
        }
}
