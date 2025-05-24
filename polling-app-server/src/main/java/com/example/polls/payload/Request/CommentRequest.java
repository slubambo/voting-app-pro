package com.example.polls.payload.Request;

import javax.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
