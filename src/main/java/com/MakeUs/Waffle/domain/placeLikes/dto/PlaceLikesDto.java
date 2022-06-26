package com.MakeUs.Waffle.domain.placeLikes.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceLikesDto {
    private Boolean isPlaceLike;
    private Long likeCnt;

    @Builder
    public PlaceLikesDto(Boolean isPlaceLike, Long likeCnt) {
        this.isPlaceLike = isPlaceLike;
        this.likeCnt = likeCnt;
    }

    public PlaceLikesDto() {
    }
}
