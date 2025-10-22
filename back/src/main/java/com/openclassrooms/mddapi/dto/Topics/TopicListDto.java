package com.openclassrooms.mddapi.dto.Topics;

import java.util.List;

import lombok.Data;

@Data
public class TopicListDto {
    private List<TopicDto> topics;
}
