package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.dto.Users.UserEditRequestDto;
import com.openclassrooms.mddapi.dto.Users.UserEditResponseDto;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.interfaces.AuthenticationServiceInterface;
import com.openclassrooms.mddapi.interfaces.UserServiceInterface;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationServiceInterface authenticationService;

    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
            AuthenticationServiceInterface authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetailsDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setId(id);
            userDetailsDto.setEmail(user.getEmail());
            userDetailsDto.setName(user.getName());
            userDetailsDto.setTopics(user.getTopics());
            userDetailsDto.setCreated_at(user.getCreatedAt());
            userDetailsDto.setUpdated_at(user.getUpdatedAt());
            return userDetailsDto;
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public UserDetailsDto getUserDetails() {

        Integer id = authenticationService.getAuthenticatedUser().getId();
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setId(id);
            userDetailsDto.setEmail(user.get().getEmail());
            userDetailsDto.setName(user.get().getName());
            userDetailsDto.setCreated_at(user.get().getCreatedAt());
            userDetailsDto.setUpdated_at(user.get().getUpdatedAt());
            return userDetailsDto;
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public UserEditResponseDto editUser(UserEditRequestDto UserEditRequestDto) {
        Integer id = authenticationService.getAuthenticatedUser().getId();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        User updatedUser = new User();
        updatedUser.setId(id);
        if (UserEditRequestDto.getUsername() != null) {
            updatedUser.setName(UserEditRequestDto.getUsername());
        } else {
            updatedUser.setName(user.getName());
        }
        if (UserEditRequestDto.getEmail() != null) {
            updatedUser.setEmail(UserEditRequestDto.getEmail());
        } else {
            updatedUser.setEmail(user.getEmail());
        }
        if (UserEditRequestDto.getEmail() != null) {
            updatedUser.setPassword(passwordEncoder.encode(UserEditRequestDto.getPassword()));
        } else {
            updatedUser.setPassword(user.getPassword());
        }
        updatedUser.setTopics(user.getTopics());
        updatedUser.setCreatedAt(user.getCreatedAt());
        updatedUser.setUpdatedAt(LocalDateTime.now());

        userRepository.save(updatedUser);

        UserEditResponseDto editResponse = new UserEditResponseDto();
        editResponse.setMessage("User updated");
        return editResponse;
    }
}
