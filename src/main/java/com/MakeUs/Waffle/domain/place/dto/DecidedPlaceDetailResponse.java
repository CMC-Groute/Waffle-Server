package com.MakeUs.Waffle.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DecidedPlaceDetailResponse {
    private Long placeId;
    private String title;
    private Long seq;

    @Builder
    public DecidedPlaceDetailResponse(String title, Long seq, Long placeId) {
        this.title = title;
        this.seq = seq;
        this.placeId = placeId;
    }

    public DecidedPlaceDetailResponse() {
    }
}
