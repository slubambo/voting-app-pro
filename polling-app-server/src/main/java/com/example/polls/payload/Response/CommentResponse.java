package com.example.polls.payload.Response;

public class CommentResponse {

    private Long id;
    private Long userId;
    private String content;
    private String createdAt;

    public CommentResponse(Long id, Long userId, String content, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
