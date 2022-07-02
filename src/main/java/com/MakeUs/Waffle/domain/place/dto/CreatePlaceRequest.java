package com.MakeUs.Waffle.domain.place.dto;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.place.Place;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreatePlaceRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String comment;
    private String link;
    private String roadNameAddress;
    private String longitude;
    private String latitude;

    public CreatePlaceRequest() {
    }

    @Builder
    public CreatePlaceRequest(String title, String comment, String link, String roadNameAddress, String longitude, String latitude) {
        this.title = title;
        this.comment = comment;
        this.link = link;
        this.roadNameAddress = roadNameAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Place toEntity(Long placeCategoryId, Invitation invitation) {
        return Place.builder()
                .comment(comment)
                .placeCategoryId(placeCategoryId)
                .roadNameAddress(roadNameAddress)
                .link(link)
                .title(title)
                .longitude(longitude)
                .latitude(latitude)
                .isDecision(false)
                .invitation(invitation)
                .build();
    }
}
