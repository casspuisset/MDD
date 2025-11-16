package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Articles.ArticleDto;
import com.openclassrooms.mddapi.dto.Articles.ArticlesDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleResponseDto;
import com.openclassrooms.mddapi.interfaces.ArticleServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/articles")
@Tag(name = "Articles")
public class ArticleController {

    private final ArticleServiceInterface articleService;

    public ArticleController(
            ArticleServiceInterface articleService) {
        this.articleService = articleService;
    }

    /**
     * Fetch all articles from the database
     * 
     * @return all articles from database
     */
    @Operation(description = "Get all articles", responses = {
            @ApiResponse(description = "All articles retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    })
    @GetMapping("/all")
    public ResponseEntity<ArticlesDto> getAllArticles() {
        ArticlesDto articlesResponseDto = articleService.getAllArticles();
        return ResponseEntity.ok().body(articlesResponseDto);
    }

    /**
     * Return an article when id is given and article exists
     * 
     * @param id id of requested article
     * @return the corresponding article or an error if article does not exist
     */
    @Operation(description = "Get a user by their ID", responses = {
            @ApiResponse(description = "Article fetched successfully", responseCode = "200"),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Article not found", responseCode = "404", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getAnArticle(@PathVariable String id) {
        ArticleDto article = articleService.getAnArticle(Integer.parseInt(id));
        return ResponseEntity.ok().body(article);
    }

    /**
     * Fetch articles for user's feed
     * 
     * @return articles from topics subscribed by the user
     */
    @Operation(description = "Get all articles from the user feed", responses = {
            @ApiResponse(description = "Articles retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    @GetMapping("/feed")
    public ResponseEntity<List<ArticleDto>> getArticlesFromUserTopics() {
        List<ArticleDto> articlesResponseDto = articleService.getArticlesFromUsersTopics();
        return ResponseEntity.ok().body(articlesResponseDto);
    }

    /**
     * Create a new article
     * 
     * @param articleRequestDto datas for the new article given by the user
     * @return a status response
     */
    @PostMapping("/new")
    @Operation(description = "Create a new article", responses = {
            @ApiResponse(description = "Article created", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"Article created\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content) })
    public ResponseEntity<CreateArticleResponseDto> createArticles(
            @RequestBody CreateArticleRequestDto articleRequestDto) {
        CreateArticleResponseDto articleResponseDto = articleService.createArticle(articleRequestDto);
        return ResponseEntity.ok().body(articleResponseDto);
    }

}
