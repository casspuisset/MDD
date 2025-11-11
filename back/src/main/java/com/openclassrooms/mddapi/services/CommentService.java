package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Comments.CommentCreationRequestDto;
import com.openclassrooms.mddapi.dto.Comments.CommentCreationResponseDto;
import com.openclassrooms.mddapi.dto.Comments.CommentDto;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.services.Auth.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper, UserRepository userRepository,
            ArticleRepository articleRepository,
            CommentRepository commentRepository,
            AuthenticationService authenticationService) {
        this.articleRepository = articleRepository;
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
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

    // get all comments from an article
    public List<CommentDto> retrieveComments(Integer id) {
        var article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            throw new NotFoundException("Error: Article doesn't exist");
        }
        var listComments = commentRepository.findByArticleId(id);
        var listCommentDto = listComments.stream().map(this::commentToDto).collect(Collectors.toList());
        return listCommentDto;
    }

    // map a comment in a Dto
    private CommentDto commentToDto(Comment comment) {
        var user = userRepository.findById(comment.getUserId()).orElse(null);
        CommentDto commentDto = commentMapper.mapToDto(user, comment);
        return commentDto;
    }

}
