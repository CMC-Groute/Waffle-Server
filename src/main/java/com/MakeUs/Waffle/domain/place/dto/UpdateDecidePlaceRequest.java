package com.MakeUs.Waffle.domain.place.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateDecidePlaceRequest {
    private List<PlaceSeqDto> placeSeqDtos;

    @Builder
    public UpdateDecidePlaceRequest(List<PlaceSeqDto> placeSeqDtos) {
        this.placeSeqDtos = placeSeqDtos;
    }
}
