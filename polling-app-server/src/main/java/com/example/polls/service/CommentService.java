package com.example.polls.service;


import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Comment;
import com.example.polls.model.Poll;
import com.example.polls.model.User;
import com.example.polls.payload.Request.CommentRequest;
import com.example.polls.payload.Response.ApiResponse;
import com.example.polls.payload.Response.CommentResponse;
import com.example.polls.payload.Response.PagedResponse;
import com.example.polls.repository.CommentRepository;
import com.example.polls.repository.PollRepository;
import com.example.polls.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    public CommentResponse createComment(Long pollId, CommentRequest commentRequest, Long userId){
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(()-> new ResourceNotFoundException("Poll", "id", pollId));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setPoll(poll);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        // 5. 응답 DTO 생성

        Instant createdAt = savedComment.getCreatedAt();
        LocalDateTime ldt = LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault());
        String createdAtFormatted = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        return new CommentResponse(
                savedComment.getId(),
                user.getId(),
                savedComment.getContent(),
                createdAtFormatted
        );

    }

    public PagedResponse<CommentResponse> getAllComment(Long pollId, Long userId, int page, int size) {


        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comment> comments = commentRepository.findByPollId(pollId, pageable);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<CommentResponse> commentResponses = comments.getContent().stream()
                .map(comment -> {
                    String createdAtFormatted = LocalDateTime.ofInstant(
                            comment.getCreatedAt(), ZoneId.systemDefault()
                    ).format(formatter);

        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getContent(),
                createdAtFormatted

        );
                })
                .collect(Collectors.toList());

        return new PagedResponse<>(
                commentResponses,
                comments.getNumber(),
                comments.getSize(),
                comments.getTotalElements(),
                comments.getTotalPages(),
                comments.isLast());

    }

    public ApiResponse deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Comment", "id", commentId);
        }

        commentRepository.delete(comment);

        return new ApiResponse(true, "comment deleted");
    }
}
