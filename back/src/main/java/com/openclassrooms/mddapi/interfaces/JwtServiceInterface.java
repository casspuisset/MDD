package com.openclassrooms.mddapi.interfaces;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletResponse;

public interface JwtServiceInterface {
    /**
     * Generate a JSON Web Token if user is correctly authenticated
     * 
     * @param authentication authentication for the user
     * @return a jwt in a string
     */
    String generateToken(Authentication authentication);

    /**
     * Add auth cookie with jwt to authenticate user request
     * 
     * @param token    jwt token for authentication
     * @param response response to edit with the token
     */
    void addAuthCookie(
            String token,
            HttpServletResponse response);

    /**
     * Delete auth cookie to revoke authentication for future request
     * 
     * @param response the httresponse to edit without the token
     */
    void deleteAuthCookie(HttpServletResponse response);
}
