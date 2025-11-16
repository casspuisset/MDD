package com.openclassrooms.mddapi.interfaces;

import org.springframework.http.ResponseEntity;

import com.openclassrooms.mddapi.dto.Auth.AuthRequestDto;
import com.openclassrooms.mddapi.dto.Auth.AuthResponseDto;
import com.openclassrooms.mddapi.dto.Auth.RegisterRequestDto;

import jakarta.servlet.http.HttpServletResponse;

public interface LoginServiceInterface {
    /**
     * Log a user and generate a jwt token with a cookie to authenticate them
     * 
     * @param loginRequestDto the login request with email and password from the
     *                        user
     * @param response        the http response to edit to authenticate the user
     * @return an authentication token
     */
    AuthResponseDto login(final AuthRequestDto loginRequestDto, HttpServletResponse response);

    /**
     * Register a new user and generate a jwt token with a cookie to authenticate
     * them
     * 
     * @param registerRequestDto the register request with datas from the new user
     * @param response           the http response to edit to authenticate the user
     * @return an authentication token
     */
    AuthResponseDto register(RegisterRequestDto registerRequestDto, HttpServletResponse response);

    /**
     * Logout a user and delete the auth cookie from the response
     * 
     * @param response the response to edit to unauthenticate the user
     * @return an empty responseEntity
     */
    ResponseEntity<Void> logout(HttpServletResponse response);

}
