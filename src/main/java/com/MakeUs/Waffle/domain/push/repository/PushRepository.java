package com.MakeUs.Waffle.domain.push.repository;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.push.Push;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushRepository extends JpaRepository<Push, Long> {
    List<Push> getByUserId(Long userId);
}
