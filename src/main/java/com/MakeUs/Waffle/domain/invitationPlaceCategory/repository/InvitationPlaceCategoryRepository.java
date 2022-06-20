package com.MakeUs.Waffle.domain.invitationPlaceCategory.repository;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationPlaceCategoryRepository extends JpaRepository<InvitationPlaceCategory, Long> {
}
