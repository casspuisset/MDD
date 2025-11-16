package com.openclassrooms.mddapi.interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.Topics.TopicDto;
import com.openclassrooms.mddapi.dto.Topics.TopicListDto;
import com.openclassrooms.mddapi.dto.Topics.TopicSubscribingResponseDto;
import com.openclassrooms.mddapi.models.Topic;

public interface TopicServiceInterface {
    TopicSubscribingResponseDto subscribeTopic(Integer topic_id);

    TopicSubscribingResponseDto unsubscribeTopic(Integer topic_id);

    TopicListDto retrieveUserTopics();

    List<Topic> getAllTopics();

    TopicDto topicToDto(Topic topic);
}
