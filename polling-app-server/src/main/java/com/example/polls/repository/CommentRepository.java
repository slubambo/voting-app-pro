package com.example.polls.repository;

import com.example.polls.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPollIdOrderByCreatedAtDesc(Long pollId);

    Page<Comment> findByPollId(Long pollId, Pageable pageable);
}
