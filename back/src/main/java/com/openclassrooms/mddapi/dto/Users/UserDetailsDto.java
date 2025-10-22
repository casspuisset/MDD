package com.openclassrooms.mddapi.dto.Users;

import java.time.LocalDateTime;
import java.util.List;

import com.openclassrooms.mddapi.models.Topic;

import lombok.Data;

@Data
public class UserDetailsDto {
    private Integer id;

    private String name;

    private String email;

    private List<Topic> topics;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
