package com.MakeUs.Waffle.domain.place.repository;

import com.MakeUs.Waffle.domain.place.Place;
import com.MakeUs.Waffle.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query(value = "SELECT b FROM Place b join fetch b.invitation a where a.id = :invitationId AND b.isDecision = true ")
    List<Place> getByInvitationIdAndIsDecisionTrue(@Param(value = "invitationId") Long invitationId);
}
