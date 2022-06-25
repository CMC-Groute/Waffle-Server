package com.MakeUs.Waffle.domain.invitationPlaceCategory.repository;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.PlaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationPlaceCategoryRepository extends JpaRepository<InvitationPlaceCategory, Long> {
    InvitationPlaceCategory getByInvitationAndPlaceCategory(Invitation invitation, PlaceCategory placeCategory);

    List<InvitationPlaceCategory> getByInvitationId(Long invitationId);
}
