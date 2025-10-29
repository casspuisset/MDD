package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Topics.TopicListDto;
import com.openclassrooms.mddapi.dto.Topics.TopicSubscribingResponseDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@Slf4j
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    // subscribe to a topic
    @PostMapping("/subscribe/{topic_id}")
    public ResponseEntity<TopicSubscribingResponseDto> subcribeTopic(@PathVariable("topic_id") String topic_id) {
        TopicSubscribingResponseDto subscribeReponse = topicService.subscribeTopic(Integer.parseInt(topic_id));
        return ResponseEntity.ok().body(subscribeReponse);
    }

    // unsubscribe to a topic
    @DeleteMapping("/unsubscribe/{topic_id}")
    public ResponseEntity<TopicSubscribingResponseDto> unsubcribeTopic(@PathVariable("topic_id") String topic_id) {
        TopicSubscribingResponseDto unsubscribeResponse = topicService.unsubscribeTopic(Integer.parseInt(topic_id));
        return ResponseEntity.ok().body(unsubscribeResponse);
    }

    // fetch users topics
    @GetMapping("/feed")
    public ResponseEntity<TopicListDto> getUserTopics() {
        TopicListDto allTopicsResponse = this.topicService.retrieveUserTopics();
        return ResponseEntity.ok().body(allTopicsResponse);
    }

    // fetch all topics
    @GetMapping("/all")
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> allTopics = this.topicService.getAllTopics();
        return ResponseEntity.ok().body(allTopics);
    }

}
