package com.example.forum.controller.dto;

import com.example.forum.model.Response;
import com.example.forum.model.Topic;
import com.example.forum.model.TopicStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopicDetailDto {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private String authorName;
    private TopicStatus status;
    private List<ResponseDto> responseList;

    public TopicDetailDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.createdAt = topic.getCreatedAt();
        this.authorName = topic.getAuthor().getName();
        this.status = topic.getStatus();
        this.responseList = new ArrayList<>();
        this.responseList.addAll(
                topic.getRespostas()
                        .stream()
                        .map(ResponseDto::new)
                        .collect(Collectors.toList())
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAuthorName() {
        return authorName;
    }

    public TopicStatus getStatus() {
        return status;
    }

    public List<ResponseDto> getResponseList() {
        return responseList;
    }
}
