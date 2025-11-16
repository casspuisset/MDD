package com.openclassrooms.mddapi.interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.Articles.ArticleDto;
import com.openclassrooms.mddapi.dto.Articles.ArticlesDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleResponseDto;
import com.openclassrooms.mddapi.models.Article;

public interface ArticleServiceInterface {

    /**
     * Retrieve all articles
     * 
     * @return a list of articles
     */
    ArticlesDto getAllArticles();

    /**
     * Retrieve a single article if it exists
     * 
     * @param id if of the article
     * @return an article
     */
    ArticleDto getAnArticle(Integer id);

    /**
     * Retrieve all articles from a user feed
     * 
     * @return a list of articles
     */
    List<ArticleDto> getArticlesFromUsersTopics();

    /**
     * Create a new article
     * 
     * @param request datas to create the new article
     * @return a message or an error if creation succeed or failed
     */
    CreateArticleResponseDto createArticle(CreateArticleRequestDto request);

    /**
     * Map an article model to a dto to manipulate it
     * 
     * @param article the original article
     * @return the dto with original article's data
     */
    ArticleDto articleToDto(Article article);
}
