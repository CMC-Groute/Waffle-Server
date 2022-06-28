package com.MakeUs.Waffle.domain.invitationPlaceCategory;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.PlaceCategoryDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitation_place_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public CreatePlaceCategoryResponse toCreatePlaceCategoryResponse(){
        return CreatePlaceCategoryResponse.builder()
                .placeCategoryId(id)
                .name(placeCategory.toString())
                .build();
    }

    public PlaceCategoryDto toPlaceCategoryDto() {
        return PlaceCategoryDto.builder()
                .placeCategoryName(placeCategory.toString())
                .placeCategoryId(id)
                .build();
    }
}
