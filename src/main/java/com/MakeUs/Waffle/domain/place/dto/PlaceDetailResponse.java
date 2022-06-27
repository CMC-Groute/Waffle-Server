package com.MakeUs.Waffle.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceDetailResponse {
    private String link;
    private String comment;

    @Builder
    public PlaceDetailResponse(String link, String comment) {
        this.link = link;
        this.comment = comment;
    }

    public PlaceDetailResponse() {
    }
}
