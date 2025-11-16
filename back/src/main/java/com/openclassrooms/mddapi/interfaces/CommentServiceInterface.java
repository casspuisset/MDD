package com.openclassrooms.mddapi.interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.Comments.CommentCreationRequestDto;
import com.openclassrooms.mddapi.dto.Comments.CommentCreationResponseDto;
import com.openclassrooms.mddapi.dto.Comments.CommentDto;
import com.openclassrooms.mddapi.models.Comment;

public interface CommentServiceInterface {
    /**
     * Create a new comment
     * 
     * @param id      id of the article where the comment is posted
     * @param comment content of this comment
     * @return an informative message for the user
     */
    CommentCreationResponseDto createComment(Integer id, CommentCreationRequestDto comment);

    /**
     * Fetch all comments from an article
     * 
     * @param id id of the target article
     * @return a liste of all comments posted on the target article
     */
    List<CommentDto> retrieveComments(Integer id);

    /**
     * Map a comment model to a dto to manipulate it
     * 
     * @param comment the original comment
     * @return a dto with original comment's datas
     */
    CommentDto commentToDto(Comment comment);

}
