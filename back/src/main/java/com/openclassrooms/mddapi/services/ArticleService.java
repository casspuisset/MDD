package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Articles.ArticleDto;
import com.openclassrooms.mddapi.dto.Articles.ArticlesDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleRequestDto;
import com.openclassrooms.mddapi.dto.Articles.CreateArticleResponseDto;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.Auth.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public ArticleService(UserRepository userRepository, ArticleRepository articleRepository,
            AuthenticationService authenticationService) {
        this.articleRepository = articleRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    // get all articles
    public ArticlesDto getAllArticles() {
        ArticlesDto articles = new ArticlesDto();
        var listArticles = articleRepository.findAll();
        var listArticlesDto = listArticles.stream().map(this::articleToDto).collect(Collectors.toList());
        articles.setArticles(listArticlesDto);
        return articles;
    }

    // get an article by its id
    public ArticleDto getAnArticle(Integer id) {
        Article searchedArticle = articleRepository.findById(id).orElse(null);
        if (searchedArticle == null) {
            throw new NotFoundException("Article not found");
        }
        ArticleDto article = articleToDto(searchedArticle);
        return article;
    }

    // // get articles with topics subscribed by the user
    public ArticlesDto getArticlesFromUsersTopics(Integer id) {

        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        ArticlesDto articles = new ArticlesDto();
        List<ArticleDto> articlesList = new ArrayList<>();

        List<Topic> usersTopicsList = user.getTopics();
        for (Topic topic : usersTopicsList) {
            Integer topicId = topic.getId();
            List<ArticleDto> thesesArticles = articleRepository.findByTopicId(topicId).stream()
                    .map(this::articleToDto)
                    .collect(Collectors.toList());
            articlesList = Stream.concat(articlesList.stream(), thesesArticles.stream()).collect(Collectors.toList());
        }

        articles.setArticles(articlesList);
        return articles;
    }

    // create a new article
    public CreateArticleResponseDto createArticle(CreateArticleRequestDto request) {
        Article newArticle = new Article();
        Integer id = authenticationService.getAuthenticatedUser().getId();

        newArticle.setTitle(request.getTitle());
        newArticle.setDescription(request.getDescription());
        newArticle.setTopicId(request.getTopicId());
        newArticle.setCreatorId(id);
        newArticle.setCreatedAt(LocalDateTime.now());
        newArticle.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(newArticle);

        CreateArticleResponseDto response = new CreateArticleResponseDto();
        response.setMessage("Article created");
        return response;
    }

    // map an article in a Dto
    private ArticleDto articleToDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setDescription(article.getDescription());
        articleDto.setTopicId(article.getTopicId());
        articleDto.setCreatorId(article.getCreatorId());
        articleDto.setCreatedAt(article.getCreatedAt());
        articleDto.setUpdatedAt(article.getUpdatedAt());
        return articleDto;
    }
}
