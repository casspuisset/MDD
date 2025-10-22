package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.Users.UserDetailsDto;
import com.openclassrooms.mddapi.models.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDetailsDto, User> {
}
