package com.MakeUs.Waffle.domain.invitation.service;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationCreateRequest;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.dto.UserSignUpRequest;
import com.MakeUs.Waffle.domain.user.exception.NotFoundUserException;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Getter
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final InvitationMemberRepository invitationMemberRepository;

    public InvitationService(InvitationRepository invitationRepository, UserRepository userRepository, InvitationMemberRepository invitationMemberRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.invitationMemberRepository = invitationMemberRepository;
    }

    @Transactional
    public Long createInvitation(Long userId, InvitationCreateRequest invitationCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(ErrorCode.NOT_FOUND_RESOURCE_ERROR));

        Invitation invitation = Invitation.builder()
                .invitationCode(this.getInvitationCode())
                .comment(invitationCreateRequest.getComment())
                .date(invitationCreateRequest.getDate())
                .invitationPlace(invitationCreateRequest.getInvitationPlace())
                .title(invitationCreateRequest.getTitle())
                .organizerId(userId)
                .build();

        InvitationMember invitationMember = InvitationMember.builder()
                .invitation(invitation)
                .user(user)
                .build();

        invitationMemberRepository.save(invitationMember);
        return invitationRepository.save(invitation).getId();
    }

    public String getInvitationCode() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
