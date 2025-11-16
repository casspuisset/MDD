package com.openclassrooms.mddapi.interfaces;

import org.springframework.http.ResponseEntity;

import com.openclassrooms.mddapi.dto.Auth.AuthRequestDto;
import com.openclassrooms.mddapi.dto.Auth.AuthResponseDto;
import com.openclassrooms.mddapi.dto.Auth.RegisterRequestDto;

import jakarta.servlet.http.HttpServletResponse;

public interface LoginServiceInterface {
    AuthResponseDto login(final AuthRequestDto loginRequestDto, HttpServletResponse response);

    AuthResponseDto register(RegisterRequestDto registerRequestDto, HttpServletResponse response);

    ResponseEntity<Void> logout(HttpServletResponse response);

}
