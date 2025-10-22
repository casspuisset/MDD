package com.openclassrooms.mddapi.dto.Articles;

import lombok.Data;

@Data
public class CreateArticleRequestDto {
    private String title;

    private String description;

    private Integer topicId;
}
