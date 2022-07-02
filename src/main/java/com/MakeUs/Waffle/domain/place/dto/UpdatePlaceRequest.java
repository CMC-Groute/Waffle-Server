package com.MakeUs.Waffle.domain.place.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePlaceRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String comment;
    private String link;
    private String roadNameAddress;
    private String longitude;
    private String latitude;
    private Long placeCategoryId;

    @Builder
    public UpdatePlaceRequest(String title, String comment, String link, String roadNameAddress, String longitude, String latitude, Long placeCategoryId) {
        this.title = title;
        this.comment = comment;
        this.link = link;
        this.roadNameAddress = roadNameAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.placeCategoryId = placeCategoryId;
    }
}
