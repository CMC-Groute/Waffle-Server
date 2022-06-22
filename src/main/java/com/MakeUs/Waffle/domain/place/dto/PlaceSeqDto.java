package com.MakeUs.Waffle.domain.place.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceSeqDto {

    private Long placeId;
    private Long seq;

    @Builder
    public PlaceSeqDto(Long placeId, Long seq) {
        this.placeId = placeId;
        this.seq = seq;
    }
}
