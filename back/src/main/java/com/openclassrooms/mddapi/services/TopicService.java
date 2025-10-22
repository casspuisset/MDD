package com.openclassrooms.mddapi.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.Topics.TopicDto;
import com.openclassrooms.mddapi.dto.Topics.TopicListDto;
import com.openclassrooms.mddapi.dto.Topics.TopicSubscribingResponseDto;
import com.openclassrooms.mddapi.exceptions.BadRequestException;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TopicService {

    private TopicRepository topicRepository;
    private UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    // subscribe to a topic
    public TopicSubscribingResponseDto subscribeTopic(Integer id, Integer topic_id) {
        User user = this.userRepository.findById(id).orElse(null);
        Topic topic = this.topicRepository.findById(topic_id).orElse(null);

        if (user == null || topic == null) {
            throw new NotFoundException("an error occured : user or topic doesn't exist");
        }
        boolean alreadySubscribed = user.getTopics().stream().anyMatch(o -> o.getId().equals(topic_id));
        if (alreadySubscribed) {
            throw new BadRequestException("User already subscribed to this topic");
        }

        user.getTopics().add(topic);
        this.userRepository.save(user);

        TopicSubscribingResponseDto subscribeResponse = new TopicSubscribingResponseDto();
        subscribeResponse.setMessage("Topic subscribed successfully");
        return subscribeResponse;
    }

    // unsubscribe to a topic
    public TopicSubscribingResponseDto unsubscribeTopic(Integer id, Integer topic_id) {
        User user = this.userRepository.findById(id).orElse(null);
        Topic topic = this.topicRepository.findById(topic_id).orElse(null);

        if (user == null || topic == null) {
            throw new NotFoundException("An error occured : user or topic doesn't exist");
        }
        boolean alreadySubscribed = user.getTopics().stream().anyMatch(o -> o.getId().equals(topic_id));
        if (!alreadySubscribed) {
            throw new BadRequestException("User not registered to this topic");
        }

        user.setTopics(user.getTopics().stream().filter(o -> !o.getId().equals(topic_id)).collect(Collectors.toList()));
        this.userRepository.save(user);

        TopicSubscribingResponseDto unsubscribeResponse = new TopicSubscribingResponseDto();
        unsubscribeResponse.setMessage("Topic unsubscribed successfully");
        return unsubscribeResponse;
    }

    // get all topics subscribed by a user
    public TopicListDto retrieveUserTopics(Integer id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        TopicListDto topics = new TopicListDto();

        var subscribedTopicsDto = user.getTopics().stream().map(this::topicToDto).collect(Collectors.toList());
        topics.setTopics(subscribedTopicsDto);
        return topics;
    }

    // get all topics
    public TopicListDto getAllTopics() {
        var topicList = new TopicListDto();
        var allTopics = topicRepository.findAll();
        var allTopicsDto = allTopics.stream().map(this::topicToDto).collect(Collectors.toList());
        topicList.setTopics(allTopicsDto);
        return topicList;
    }

    // map a topic in a Dto
    private TopicDto topicToDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());
        return topicDto;
    }

}
