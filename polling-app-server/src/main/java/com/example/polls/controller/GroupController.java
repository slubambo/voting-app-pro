package com.example.polls.controller;

import com.example.polls.model.User;
import com.example.polls.payload.Request.CreateGroupRequest;
import com.example.polls.payload.Response.GroupSummaryResponse;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.CustomUserDetailsService;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final UserRepository userRepository;

    private final GroupService groupService;

    //그룹생성
    @PostMapping
    public ResponseEntity<GroupSummaryResponse> createGroup(
            @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        System.out.println(">>> userPrincipal = " + userPrincipal);
        System.out.println(">>> request.name = " + request.getName());
        System.out.println(">>> request.memberIds = " + request.getMemberIds());

        if (userPrincipal == null) {
            throw new RuntimeException("userPrincipal이 null입니다.");
        }

        User creator = userRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new RuntimeException("사용자 없음"));

        GroupSummaryResponse response = groupService.createGroup(request, creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}