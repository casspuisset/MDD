package com.openclassrooms.mddapi.dto.Articles;

import java.util.List;

import lombok.Data;

@Data
public class ArticlesDto {
    private List<ArticleDto> articles;
}
