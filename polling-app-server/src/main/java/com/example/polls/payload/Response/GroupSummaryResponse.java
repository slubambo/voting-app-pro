package com.example.polls.payload.Response;

import com.example.polls.model.Group;



public class GroupSummaryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String joinCode;

    public GroupSummaryResponse(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.joinCode = group.getJoinCode();
        this.imageUrl = group.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }
}
