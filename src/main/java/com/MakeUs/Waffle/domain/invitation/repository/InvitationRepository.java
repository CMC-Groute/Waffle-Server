package com.MakeUs.Waffle.domain.invitation.repository;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByInvitationCode(String invitationCode);

    @Query(value = "SELECT b FROM Invitation b join fetch b.invitationMembers a where a.user.id = :userId AND b.isExpired = false order by b.date ASC NULLS LAST , b.createdAt ASC")
    List<Invitation> getByUser(@Param(value = "userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Invitation m SET m.isExpired =:isExpired  WHERE m.date = :date")
    void updateExpire(@Param("isExpired") boolean isExpired, @Param("date") LocalDate date);

    @Query("SELECT b from Invitation b where substring(b.createdAt,1,10) = :date ")
    List<Invitation> getByCreatedAt(@Param("date") String date);

    List<Invitation> getByDate(@Param("date") LocalDate date);
}