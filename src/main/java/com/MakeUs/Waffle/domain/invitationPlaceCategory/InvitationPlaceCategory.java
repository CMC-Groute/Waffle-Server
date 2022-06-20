package com.MakeUs.Waffle.domain.invitationPlaceCategory;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private PlaceCategory placeCategory;

    @Builder
    public InvitationPlaceCategory(Long id, Invitation invitation, PlaceCategory placeCategory) {
        this.id = id;
        this.invitation = invitation;
        this.placeCategory = placeCategory;
    }


    public void setInvitation(Invitation invitation) {
        if (Objects.nonNull(this.invitation)) {
            this.invitation.getInvitationMembers()
                    .remove(this);
        }
        this.invitation = invitation;
    }
}
