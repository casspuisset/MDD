package com.openclassrooms.mddapi.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.openclassrooms.mddapi.services.Auth.UserDetailsLoader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

        @org.springframework.beans.factory.annotation.Value("${security.jwt.key}")
        private String jwtKey;

        private final UserDetailsLoader userDetailsLoader;

        private static final String[] PUBLIC_PATHS = {
                        "/api/auth/register",
                        "/api/auth/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.ignoringRequestMatchers(PUBLIC_PATHS))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                                .requestMatchers(PUBLIC_PATHS).permitAll()
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt.decoder(jwtDecoder())))
                                .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);

                authenticationManagerBuilder.userDetailsService(userDetailsLoader)
                                .passwordEncoder(passwordEncoder());

                return authenticationManagerBuilder.build();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {

                return new BCryptPasswordEncoder();

        }

        @Bean
        public JwtEncoder jwtEncoder() {

                return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));

        }

        @Bean
        public JwtDecoder jwtDecoder() {

                SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length,
                                "RSA");
                return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();

        }

}
