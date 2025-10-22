package com.openclassrooms.mddapi.dto.Articles;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleDto {
    private Integer id;

    private String title;

    private String description;

    private Integer topicId;

    private Integer creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
