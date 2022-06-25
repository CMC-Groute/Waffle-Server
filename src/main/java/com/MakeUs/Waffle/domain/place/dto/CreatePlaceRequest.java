package com.MakeUs.Waffle.domain.place.dto;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.place.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePlaceRequest {
    private String title;
    private String comment;
    private String link;
    private String roadNameAddress;

    @Builder
    public CreatePlaceRequest(String title, String comment, String link, String roadNameAddress) {
        this.title = title;
        this.comment = comment;
        this.link = link;
        this.roadNameAddress = roadNameAddress;
    }

    public CreatePlaceRequest() {
    }

    public Place toEntity(Long placeCategoryId, Invitation invitation) {
        return Place.builder()
                .comment(comment)
                .placeCategoryId(placeCategoryId)
                .roadNameAddress(roadNameAddress)
                .link(link)
                .title(title)
                .isDecision(false)
                .invitation(invitation)
                .build();
    }
}
