package com.openclassrooms.mddapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Comments.CommentCreationRequestDto;
import com.openclassrooms.mddapi.dto.Comments.CommentCreationResponseDto;
import com.openclassrooms.mddapi.dto.Comments.CommentDto;
import com.openclassrooms.mddapi.dto.Comments.CommentListDto;
import com.openclassrooms.mddapi.services.CommentService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@Slf4j
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommentCreationResponseDto> postComment(@PathVariable("id") String id,
            @RequestBody CommentCreationRequestDto comment) {
        CommentCreationResponseDto commentResponse = commentService.createComment(Integer.parseInt(id), comment);

        return ResponseEntity.ok().body(commentResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsFromAnArticle(@PathVariable("id") String id) {
        List<CommentDto> commentResponse = commentService.retrieveComments(Integer.parseInt(id));
        return ResponseEntity.ok().body(commentResponse);
    }

}
