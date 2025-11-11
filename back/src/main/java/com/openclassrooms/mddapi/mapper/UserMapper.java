package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "created_at", source = "user.createdAt")
    @Mapping(target = "updated_at", source = "user.updatedAt")
    public UserDetailsDto mapToDto(User user);
}
