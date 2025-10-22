package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.Topics.TopicListDto;
import com.openclassrooms.mddapi.dto.Topics.TopicSubscribingResponseDto;
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
    @PostMapping("{id}/subscribe/{topic_id}")
    public ResponseEntity<TopicSubscribingResponseDto> subcribeTopic(@PathVariable("id") String id,
            @PathVariable("topic_id") String topic_id) {
        TopicSubscribingResponseDto subscribeReponse = topicService.subscribeTopic(Integer.parseInt(id),
                Integer.parseInt(topic_id));
        return ResponseEntity.ok().body(subscribeReponse);
    }

    // unsubscribe to a topic
    @DeleteMapping("{id}/unsubscribe/{topic_id}")
    public ResponseEntity<TopicSubscribingResponseDto> unsubcribeTopic(@PathVariable("id") String id,
            @PathVariable("topic_id") String topic_id) {
        TopicSubscribingResponseDto unsubscribeResponse = topicService.unsubscribeTopic(Integer.parseInt(id),
                Integer.parseInt(topic_id));
        return ResponseEntity.ok().body(unsubscribeResponse);
    }

    // fetch users topics
    @GetMapping("{id}/all")
    public ResponseEntity<TopicListDto> getUserTopics(@PathVariable("id") String id) {
        TopicListDto allTopicsResponse = this.topicService.retrieveUserTopics(Integer.parseInt(id));
        return ResponseEntity.ok().body(allTopicsResponse);
    }

    // fetch all topics
    @GetMapping("/all")
    public ResponseEntity<TopicListDto> getAllTopics() {
        TopicListDto allTopics = this.topicService.getAllTopics();
        return ResponseEntity.ok().body(allTopics);
    }

}
