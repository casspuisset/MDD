package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Comments.CommentCreationRequestDto;
import com.openclassrooms.mddapi.dto.Comments.CommentCreationResponseDto;
import com.openclassrooms.mddapi.dto.Comments.CommentDto;
import com.openclassrooms.mddapi.dto.Comments.CommentListDto;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.services.Auth.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {

    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;
    private final AuthenticationService authenticationService;

    public CommentService(ArticleRepository articleRepository, CommentRepository commentRepository,
            AuthenticationService authenticationService) {
        this.articleRepository = articleRepository;
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
    }

    // create a new comment
    public CommentCreationResponseDto createComment(Integer id, CommentCreationRequestDto comment) {
        var article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            throw new NotFoundException("Error: The article you want to comment doesn't exist");
        }

        Comment newComment = new Comment();
        Integer userId = authenticationService.getAuthenticatedUser().getId();
        newComment.setUserId(userId);
        newComment.setArticleId(id);
        newComment.setContent(comment.getComment());
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(newComment);

        CommentCreationResponseDto commentResponse = new CommentCreationResponseDto();
        commentResponse.setMessage("The comment has been successfully send");
        return commentResponse;
    }

    public CommentListDto retrieveComments(Integer id) {
        var article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            throw new NotFoundException("Error: Article doesn't exist");
        }
        CommentListDto commentList = new CommentListDto();
        var listComments = commentRepository.findByArticleId(id);
        var listCommentDto = listComments.stream().map(this::commentToDto).collect(Collectors.toList());
        commentList.setComment(listCommentDto);
        return commentList;
    }

    // map a comment in a Dto
    private CommentDto commentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setArticleId(comment.getArticleId());
        commentDto.setContent(comment.getContent());
        commentDto.setUserId(comment.getUserId());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setUpdatedAt(comment.getUpdatedAt());
        return commentDto;
    }

}
