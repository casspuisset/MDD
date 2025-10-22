package com.openclassrooms.mddapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.dto.Users.UserEditRequestDto;
import com.openclassrooms.mddapi.dto.Users.UserEditResponseDto;
import com.openclassrooms.mddapi.services.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/edit")
    public ResponseEntity<UserEditResponseDto> editUser(@RequestBody UserEditRequestDto userRequest) {
        UserEditResponseDto userEditResponse = userService.editUser(userRequest);
        return ResponseEntity.ok().body(userEditResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> findUserById(@PathVariable("id") String id) {
        UserDetailsDto userDetails = userService.getUserById(Integer.parseInt(id));
        return ResponseEntity.ok().body(userDetails);
    }

}
