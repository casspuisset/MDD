package com.openclassrooms.mddapi.interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.Topics.TopicDto;
import com.openclassrooms.mddapi.dto.Topics.TopicListDto;
import com.openclassrooms.mddapi.dto.Topics.TopicSubscribingResponseDto;
import com.openclassrooms.mddapi.models.Topic;

public interface TopicServiceInterface {
    /**
     * Subscribe a user from a topic
     * 
     * @param topic_id id of targeted topic
     * @return An informative message for the user
     */
    TopicSubscribingResponseDto subscribeTopic(Integer topic_id);

    /**
     * Unsubscribe a user from a topic
     * 
     * @param topic_id id of targeted topic
     * @return An informative message for the user
     */
    TopicSubscribingResponseDto unsubscribeTopic(Integer topic_id);

    /**
     * Fetch all topics subscribed from a user
     * 
     * @return A list of all topics subscribed by the user
     */
    TopicListDto retrieveUserTopics();

    /**
     * Fetch all topics from the database
     * 
     * @return A list of all topics from the database
     */
    List<Topic> getAllTopics();

    /**
     * Map a topic model to a dto to manipulate it
     * 
     * @param topic the original topic object
     * @return a dto with original topic's datas
     */
    TopicDto topicToDto(Topic topic);
}
