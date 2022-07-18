package com.MakeUs.Waffle.domain.push.repository;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.push.Push;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PushRepository extends JpaRepository<Push, Long> {
    List<Push> getByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("delete from Push p where substring(p.createdAt,1,10) = :date")
    void deleteExpire(@Param("date") LocalDate date);
}
