package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Articles.ArticleDto;
import com.openclassrooms.mddapi.dto.Articles.ArticlesDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleResponseDto;
import com.openclassrooms.mddapi.services.ArticleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/articles")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(
            ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/all")
    public ResponseEntity<ArticlesDto> getAllArticles() {
        ArticlesDto articlesResponseDto = articleService.getAllArticles();
        return ResponseEntity.ok().body(articlesResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getAnArticle(@PathVariable String id) {
        ArticleDto article = articleService.getAnArticle(Integer.parseInt(id));
        return ResponseEntity.ok().body(article);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<ArticleDto>> getArticlesFromUserTopics() {
        List<ArticleDto> articlesResponseDto = articleService.getArticlesFromUsersTopics();
        return ResponseEntity.ok().body(articlesResponseDto);
    }

    // @PostMapping(path = "/new", consumes = "multipart/form-data")
    // public ResponseEntity<CreateArticleResponseDto> createArticles(
    // @ModelAttribute CreateArticleRequestDto articleRequestDto) {
    // CreateArticleResponseDto articleResponseDto =
    // articleService.createArticle(articleRequestDto);
    // return ResponseEntity.ok().body(articleResponseDto);
    // }

    @PostMapping("/new")
    public ResponseEntity<CreateArticleResponseDto> createArticles(
            @RequestBody CreateArticleRequestDto articleRequestDto) {
        CreateArticleResponseDto articleResponseDto = articleService.createArticle(articleRequestDto);
        return ResponseEntity.ok().body(articleResponseDto);
    }

}
