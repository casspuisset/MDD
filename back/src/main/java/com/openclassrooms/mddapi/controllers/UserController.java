package com.openclassrooms.mddapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.dto.Users.UserEditRequestDto;
import com.openclassrooms.mddapi.dto.Users.UserEditResponseDto;
import com.openclassrooms.mddapi.interfaces.UserServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Slf4j
@RequestMapping("/api/user")
@Tag(name = "Utilisateurs")
public class UserController {

    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    // edit the current user account
    @Operation(description = "Edit the active user account", responses = {
            @ApiResponse(description = "User updated", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"User updated\"}")) }),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content) })
    @PutMapping("/edit")
    public ResponseEntity<UserEditResponseDto> editUser(@RequestBody UserEditRequestDto userRequest) {
        UserEditResponseDto userEditResponse = userService.editUser(userRequest);
        return ResponseEntity.ok().body(userEditResponse);
    }

    // retrieve data from an user by their id
    @Operation(description = "Get a user by their ID", responses = {
            @ApiResponse(description = "Successfully retrieved the user by their ID", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> findUserById(@PathVariable("id") String id) {
        UserDetailsDto userDetails = userService.getUserById(Integer.parseInt(id));
        return ResponseEntity.ok().body(userDetails);
    }

}
