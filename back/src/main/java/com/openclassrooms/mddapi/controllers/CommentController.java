package com.openclassrooms.mddapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Comments.CommentCreationRequestDto;
import com.openclassrooms.mddapi.dto.Comments.CommentCreationResponseDto;
import com.openclassrooms.mddapi.dto.Comments.CommentDto;
import com.openclassrooms.mddapi.interfaces.CommentServiceInterface;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Commentaires")
public class CommentController {

    private final CommentServiceInterface commentService;

    public CommentController(CommentServiceInterface commentService) {
        this.commentService = commentService;
    }

    /**
     * Create a new comment
     * 
     * @param id      id of the article connected to this comment
     * @param comment message from the comment
     * @return response for user's information
     */
    @Operation(description = "Create a new comment", responses = {
            @ApiResponse(description = "Article created", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"The comment has been successfully send\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Article not found", responseCode = "404", content = @Content)
    })
    @PostMapping("/{id}")
    public ResponseEntity<CommentCreationResponseDto> postComment(@PathVariable("id") String id,
            @RequestBody CommentCreationRequestDto comment) {
        CommentCreationResponseDto commentResponse = commentService.createComment(Integer.parseInt(id), comment);

        return ResponseEntity.ok().body(commentResponse);
    }

    /**
     * Get all comments from an article
     * 
     * @param id of the article connected to these comments
     * @return a list with all comments connected to this article
     */
    @Operation(description = "Get all comments from an article", responses = {
            @ApiResponse(description = "Comments retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Article not found", responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsFromAnArticle(@PathVariable("id") String id) {
        List<CommentDto> commentResponse = commentService.retrieveComments(Integer.parseInt(id));
        return ResponseEntity.ok().body(commentResponse);
    }

}
