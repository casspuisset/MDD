package com.openclassrooms.mddapi.configuration;

import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

        @org.springframework.beans.factory.annotation.Value("${security.jwt.key}")
        private String jwtKey;

        private final UserDetailsService userDetailsLoader;

        private final BearerTokenResolver cookieTokenResolver;

        private static final String[] PUBLIC_PATHS = {
                        "/api/auth/register",
                        "/api/auth/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.ignoringRequestMatchers(PUBLIC_PATHS))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                                .requestMatchers(PUBLIC_PATHS).permitAll()
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .bearerTokenResolver(cookieTokenResolver)
                                                .jwt(Customizer.withDefaults()))
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
        /**
         * Hash password for security
         * 
         * @return
         */
        public BCryptPasswordEncoder passwordEncoder() {

                return new BCryptPasswordEncoder();

        }

        @Bean
        /**
         * Encode token with security key
         * 
         * @return encoded token
         */
        public JwtEncoder jwtEncoder() {

                return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));

        }

        @Bean
        /**
         * Decode token with security key
         * 
         * @return decoded token
         */
        public JwtDecoder jwtDecoder() {

                SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length,
                                "RSA");
                return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();

        }

        @Bean
        /**
         * Set configuration for cors-connexion
         * 
         * @return
         */
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("http://localhost:4200",
                                "http://localhost"));
                config.setAllowedMethods(
                                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                config.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }

}
