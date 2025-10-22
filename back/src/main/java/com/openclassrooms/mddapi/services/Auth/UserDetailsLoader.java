package com.openclassrooms.mddapi.services.Auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsLoader implements UserDetailsService {

    private final UserRepository userRepository;

    // retrieve the informations of an user by searching their email in the database
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException("No user found with this email");
        }
    }
}
