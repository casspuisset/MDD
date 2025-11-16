package com.openclassrooms.mddapi.interfaces;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletResponse;

public interface JwtServiceInterface {
    String generateToken(Authentication authentication);

    void addAuthCookie(
            String token,
            HttpServletResponse response);

    void deleteAuthCookie(HttpServletResponse response);
}
