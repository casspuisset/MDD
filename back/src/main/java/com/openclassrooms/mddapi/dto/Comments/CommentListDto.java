package com.openclassrooms.mddapi.dto.Comments;

import java.util.List;

import lombok.Data;

@Data
public class CommentListDto {
    private List<CommentDto> comment;
}
