package com.MakeUs.Waffle.domain.invationMember.repository;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationMemberRepository extends JpaRepository<InvitationMember, Long> {
    List<InvitationMember> findByUserId(Long userId);

    Optional<InvitationMember> findByUserIdAndInvitationId(Long userId, Long invitationId);

    boolean existsByUserIdAndInvitationId(Long userId, Long invitationId);

    Optional<List<InvitationMember>> findByInvitation(Invitation invitation);

    List<InvitationMember> getByInvitationId(Long invitationId);

    List<InvitationMember> deleteByUser(User user);
}
