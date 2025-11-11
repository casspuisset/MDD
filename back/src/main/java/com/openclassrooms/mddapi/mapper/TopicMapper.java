package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;

import com.openclassrooms.mddapi.dto.Topics.TopicDto;
import com.openclassrooms.mddapi.models.Topic;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    public TopicDto mapToDto(Topic topic);
}
