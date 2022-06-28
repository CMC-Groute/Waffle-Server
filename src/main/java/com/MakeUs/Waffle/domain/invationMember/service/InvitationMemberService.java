package com.MakeUs.Waffle.domain.invationMember.service;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Getter
public class InvitationMemberService {
    private InvitationMemberRepository invitationMemberRepository;
    private InvitationRepository invitationRepository;

    public InvitationMemberService(InvitationMemberRepository invitationMemberRepository, InvitationRepository invitationRepository) {
        this.invitationMemberRepository = invitationMemberRepository;
        this.invitationRepository = invitationRepository;
    }

    @Transactional
    public void exitInvitationMember(Long userId, Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));

        InvitationMember invitationMember = invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId)
                .orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));

        invitationMemberRepository.delete(invitationMember);
        List<InvitationMember> invitationMembers = invitationMemberRepository.getByInvitationId(invitationId);
        if(invitationMembers.size() == 0){
            System.out.println("isnull");
            invitationRepository.delete(invitation);
        }
    }
}
