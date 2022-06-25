package com.MakeUs.Waffle.domain.invitation.repository;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByInvitationCode(String invitationCode);

    @Query(value = "SELECT b FROM Invitation b join fetch b.invitationMembers a where a.user.id = :userId order by b.date ASC NULLS LAST , b.createdAt ASC")
    List<Invitation> getByUser(@Param(value = "userId") Long userId);
}