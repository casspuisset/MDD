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
import com.openclassrooms.mddapi.interfaces.TopicServiceInterface;
import com.openclassrooms.mddapi.models.Topic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@Slf4j
@RequestMapping("/api/topics")
@Tag(name = "Thèmes")
public class TopicController {

    private final TopicServiceInterface topicService;

    public TopicController(TopicServiceInterface topicService) {
        this.topicService = topicService;
    }

    // subscribe to a topic
    @Operation(description = "Get a user by their ID", responses = {
            @ApiResponse(description = "Successfully subscribe to the topic", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"Topic subscribed successfully\"}")) }),
            @ApiResponse(description = "User already registered to this topic", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Topic not found", responseCode = "404", content = @Content) })
    @PostMapping("/subscribe/{topic_id}")
    public ResponseEntity<TopicSubscribingResponseDto> subcribeTopic(@PathVariable("topic_id") String topic_id) {
        TopicSubscribingResponseDto subscribeReponse = topicService.subscribeTopic(Integer.parseInt(topic_id));
        return ResponseEntity.ok().body(subscribeReponse);
    }

    // unsubscribe to a topic
    @Operation(description = "Edit the active user account", responses = {
            @ApiResponse(description = "Successfully unsubscribe to the topic", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"Topic unsubscribed successfully\"}")) }),
            @ApiResponse(description = "User not registered to this topic", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Topic not found", responseCode = "404", content = @Content) })
    @DeleteMapping("/unsubscribe/{topic_id}")
    public ResponseEntity<TopicSubscribingResponseDto> unsubcribeTopic(@PathVariable("topic_id") String topic_id) {
        TopicSubscribingResponseDto unsubscribeResponse = topicService.unsubscribeTopic(Integer.parseInt(topic_id));
        return ResponseEntity.ok().body(unsubscribeResponse);
    }

    // fetch users topics
    @Operation(description = "Get all topics from the user feed", responses = {
            @ApiResponse(description = "Topics retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    @GetMapping("/feed")
    public ResponseEntity<TopicListDto> getUserTopics() {
        TopicListDto allTopicsResponse = this.topicService.retrieveUserTopics();
        return ResponseEntity.ok().body(allTopicsResponse);
    }

    // fetch all topics
    @Operation(description = "Retrieve all topics from the database", responses = {
            @ApiResponse(description = "All topics retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    })
    @GetMapping("/all")
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> allTopics = this.topicService.getAllTopics();
        return ResponseEntity.ok().body(allTopics);
    }

}
