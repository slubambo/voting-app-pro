package com.example.polls.repository;

import com.example.polls.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByJoinCode(String joinCode); //코드로 그룹조회
    List<Group> findAllByMembers_user_Id(Long userId);
    boolean existsByJoinCode(String joinCode); //중복방지
}
