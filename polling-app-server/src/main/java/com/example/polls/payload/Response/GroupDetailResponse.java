package com.example.polls.payload.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GroupDetailResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private List<MemberSummary> members;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberSummary {
        private Long id;
        private String username;
        private String email;
    }
}
