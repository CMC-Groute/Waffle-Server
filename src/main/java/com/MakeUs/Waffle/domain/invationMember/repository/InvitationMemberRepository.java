package com.MakeUs.Waffle.domain.invationMember.repository;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationMemberRepository extends JpaRepository<InvitationMember, Long> {
    List<InvitationMember> findByUserId(Long userId);
}
