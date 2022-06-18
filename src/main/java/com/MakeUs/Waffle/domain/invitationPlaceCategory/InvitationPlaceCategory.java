package com.MakeUs.Waffle.domain.invitationPlaceCategory;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "invitation_place_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationPlaceCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    private Long placeCategoryId;
}
