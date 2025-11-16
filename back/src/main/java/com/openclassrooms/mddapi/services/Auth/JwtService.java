package com.openclassrooms.mddapi.services.Auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.interfaces.JwtServiceInterface;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService implements JwtServiceInterface {

        private JwtEncoder jwtEncoder;

        public JwtService(JwtEncoder jwtEncoder) {
                this.jwtEncoder = jwtEncoder;

        }

        public String generateToken(Authentication authentication) {

                Instant now = Instant.now();
                Instant expiration = now.plus(1, ChronoUnit.HOURS);

                JwtClaimsSet claims = JwtClaimsSet.builder()
                                .subject(authentication.getName())
                                .issuedAt(now)
                                .expiresAt(expiration)
                                .build();

                JwsHeader header = JwsHeader.with(MacAlgorithm.HS256)
                                .type("JWT")
                                .build();

                JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(header,
                                claims);

                return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        }

        public void addAuthCookie(
                        String token,
                        HttpServletResponse response) {
                Cookie authCookie = new Cookie("token", token);
                authCookie.setMaxAge(24 * 60 * 60);
                authCookie.setSecure(false);
                authCookie.setHttpOnly(true);
                authCookie.setPath("/");
                response.addCookie(authCookie);
        }

        public void deleteAuthCookie(HttpServletResponse response) {
                Cookie authCookie = new Cookie("token", null);
                authCookie.setMaxAge(0);
                authCookie.setSecure(false);
                authCookie.setHttpOnly(true);
                authCookie.setPath("/");
                response.addCookie(authCookie);
        }
}