package com.example.polls.controller;

import com.example.polls.payload.Request.CommentRequest;
import com.example.polls.payload.Response.ApiResponse;
import com.example.polls.payload.Response.CommentResponse;
import com.example.polls.payload.Response.PagedResponse;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/polls/{pollId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long pollId,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal){

        CommentResponse commentResponse = commentService.createComment(
                pollId, commentRequest, userPrincipal.getId());

        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<CommentResponse>> getAllComments(
            @PathVariable Long pollId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){

        PagedResponse<CommentResponse> response = commentService.getAllComment(pollId, userPrincipal.getId(), page, size);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserPrincipal userPrincipal){

        ApiResponse response = commentService.deleteComment(commentId, userPrincipal.getId());
        return ResponseEntity.ok(response);
    }
}
