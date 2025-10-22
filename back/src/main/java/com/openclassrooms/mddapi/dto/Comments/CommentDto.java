package com.openclassrooms.mddapi.dto.Comments;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Integer id;

    private Integer userId;

    private Integer articleId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
