package com.MakeUs.Waffle.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DecidedPlaceDetailResponse {
    private String title;
    private Long seq;

    @Builder
    public DecidedPlaceDetailResponse(String title, Long seq) {
        this.title = title;
        this.seq = seq;
    }

    public DecidedPlaceDetailResponse() {
    }
}
