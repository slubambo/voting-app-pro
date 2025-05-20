package com.example.polls.service;

import com.example.polls.exception.AlreadyJoinedException;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Group;
import com.example.polls.model.GroupMember;
import com.example.polls.model.GroupRole;
import com.example.polls.model.User;
import com.example.polls.payload.Request.CreateGroupRequest;
import com.example.polls.payload.Response.GroupDetailResponse;
import com.example.polls.payload.Response.GroupSummaryResponse;
import com.example.polls.repository.GroupMemberRepository;
import com.example.polls.repository.GroupRepository;
import com.example.polls.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public GroupSummaryResponse createGroup(CreateGroupRequest request,
                                            User creator) {
        //참여 코드 생성
        String joinCode = generateUniqueJoinCode();

        //그룹 엔티티 생성
        Group group = new Group();
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setImageUrl(request.getImageUrl());
        group.setJoinCode(joinCode);

        //그룹저장
        groupRepository.save(group);


        //생성자 그룹리더로 등록
        GroupMember creatorMember = new GroupMember();
        creatorMember.setGroup(group);
        creatorMember.setUser(creator);
        creatorMember.setRole(GroupRole.LEADER);
        groupMemberRepository.save(creatorMember);

        //추가 멤버 등록(자기자신제외)
        if(request.getMemberIds() != null) {
            for(Long memberId : request.getMemberIds()) {
                if(!memberId.equals(creator.getId())){
                    userRepository.findById(memberId).ifPresent(user -> {
                        GroupMember groupMember = new GroupMember();
                        groupMember.setGroup(group);
                        groupMember.setUser(user);
                        groupMember.setRole(GroupRole.MEMBER);
                        groupMemberRepository.save(groupMember);
                    });
                }
            }
        }

        return new GroupSummaryResponse(group);
    }
    private String generateUniqueJoinCode() {
        String code;
       do{
           code = UUID.randomUUID().toString().substring(0, 6);
       }while(groupRepository.existsByJoinCode(code));
       return code;
    }


    //내 그룹 조회
    public List<GroupSummaryResponse> getGroupsForUser(Long userId) {
        List<Group> groups = groupMemberRepository.findGroupsByUserId(userId);
        return groups.stream()
                .map(GroupSummaryResponse::new)
                .collect(Collectors.toList());
    }

    //조인 코드로 그룹 참여
    public void joinCode(Long userId, String joinCode) {
        Group group = groupRepository.findByJoinCode(joinCode)
                .orElseThrow(()-> new BadRequestException("유효하지 않은 입장코드입니다."));

        //중복 참여 체크
        boolean alreadyJoined = groupMemberRepository.existsByUserIdAndGroupId(userId, group.getId());
        if(alreadyJoined){
            throw new AlreadyJoinedException("이미 참여한 그룹입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        GroupMember newMember = new GroupMember();
        newMember.setGroup(group);
        newMember.setUser(user);
        newMember.setRole(GroupRole.MEMBER);
        groupMemberRepository.save(newMember);

    }

    public GroupDetailResponse getGroupDetail(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new ResourceNotFoundException("Group", "id", groupId));

        List<GroupDetailResponse.MemberSummary> members = group.getMembers().stream()
                .map(member-> new GroupDetailResponse.MemberSummary(
                        member.getUser().getId(),
                        member.getUser().getUsername(),
                        member.getUser().getEmail()
                ))
                .collect(Collectors.toList());

        return new GroupDetailResponse(group.getId(),group.getName(),group.getImageUrl(),members);

    }
}
