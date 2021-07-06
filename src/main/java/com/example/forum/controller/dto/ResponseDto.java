package com.example.forum.controller.dto;

import com.example.forum.model.Response;

import java.time.LocalDateTime;

public class ResponseDto {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private String authorName;

    public ResponseDto(Response response) {
        this.id = response.getId();
        this.message = response.getMessage();
        this.createdAt = response.getCreatedAt();
        this.authorName = response.getAuthor().getName();
    }

    public Long getId() {
        return id;
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
}
