package com.openclassrooms.mddapi.interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.Comments.CommentCreationRequestDto;
import com.openclassrooms.mddapi.dto.Comments.CommentCreationResponseDto;
import com.openclassrooms.mddapi.dto.Comments.CommentDto;
import com.openclassrooms.mddapi.models.Comment;

public interface CommentServiceInterface {
    CommentCreationResponseDto createComment(Integer id, CommentCreationRequestDto comment);

    List<CommentDto> retrieveComments(Integer id);

    CommentDto commentToDto(Comment comment);

}
