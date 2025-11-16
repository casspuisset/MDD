package com.openclassrooms.mddapi.interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.Articles.ArticleDto;
import com.openclassrooms.mddapi.dto.Articles.ArticlesDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleResponseDto;
import com.openclassrooms.mddapi.models.Article;

public interface ArticleServiceInterface {

    ArticlesDto getAllArticles();

    ArticleDto getAnArticle(Integer id);

    List<ArticleDto> getArticlesFromUsersTopics();

    CreateArticleResponseDto createArticle(CreateArticleRequestDto request);

    ArticleDto articleToDto(Article article);
}
