package com.example.polls.controller;

import com.example.polls.model.User;
import com.example.polls.payload.Request.CreateGroupRequest;
import com.example.polls.payload.Request.JoinGroupRequest;
import com.example.polls.payload.Response.GroupDetailResponse;
import com.example.polls.payload.Response.GroupSummaryResponse;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        User creator = userRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new RuntimeException("사용자 없음"));

        GroupSummaryResponse response = groupService.createGroup(request, creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<GroupSummaryResponse>> getMyGroups(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long useId = userPrincipal.getId();
        List<GroupSummaryResponse> groups = groupService.getGroupsForUser(useId);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinGroup(@RequestBody JoinGroupRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        groupService.joinCode(userPrincipal.getId(),request.getJoinCode());
        return ResponseEntity.ok("그룹에 성공적으로 참여했습니다.");
    }

    //그룹 상세조회
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDetailResponse> getGroupDetail(@PathVariable Long groupId,
                                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        GroupDetailResponse response = groupService.getGroupDetail(groupId,userPrincipal.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{groupId}/members")
    public ResponseEntity<List<GroupDetailResponse.MemberSummary>> getGroupMembers(@PathVariable Long groupId,
                                                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        GroupDetailResponse response = groupService.getGroupDetail(groupId, userPrincipal.getId());
        return ResponseEntity.ok(response.getMembers());
    }

}