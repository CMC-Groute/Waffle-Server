package com.MakeUs.Waffle.domain.invitation.repository;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}