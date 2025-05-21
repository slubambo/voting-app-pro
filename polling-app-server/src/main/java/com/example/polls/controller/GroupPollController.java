package com.example.polls.controller;

import com.example.polls.payload.Request.PollRequest;
import com.example.polls.payload.Request.VoteRequest;
import com.example.polls.payload.Response.ApiResponse;
import com.example.polls.payload.Response.PagedResponse;
import com.example.polls.payload.Response.PollResponse;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.GroupService;
import com.example.polls.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/groups/{groupId}/polls")
public class GroupPollController {
    @Autowired
    private PollService pollService;
    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<?> createPollInGroup (@PathVariable Long groupId,
            @CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody PollRequest request) {
        groupService.validateGroupMember(groupId, currentUser.getId());
        pollService.createPollInGroup(groupId, request, currentUser);
        return ResponseEntity.ok(new ApiResponse(true, "Poll Created Successfully"));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<PollResponse>> getAllPollInGroup(@PathVariable Long groupId,
                                                    @CurrentUser UserPrincipal userPrincipal,
                                                    @RequestParam(value = "page", defaultValue="0") int page,
                                                    @RequestParam(value = "size", defaultValue="10") int size){
        PagedResponse<PollResponse> response = pollService.getAllPollsInGroup(groupId, userPrincipal, page, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{pollId}")
    public ResponseEntity<PollResponse> getPollByIdInGroup(@PathVariable Long groupId,
                                                           @PathVariable Long pollId,
                                                           @CurrentUser UserPrincipal userPrincipal){
        PollResponse response = pollService.getPollByIdInGroup(pollId, groupId, userPrincipal);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{pollId}/votes")
    public PollResponse castVote(@CurrentUser UserPrincipal userPrincipal,
                                 @PathVariable Long pollId,
                                 @Valid @RequestBody VoteRequest voteRequest){
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, userPrincipal);
    }

}
