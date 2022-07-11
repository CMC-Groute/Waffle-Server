package com.MakeUs.Waffle.domain.place.dto;

import com.MakeUs.Waffle.domain.placeLikes.dto.PlaceLikesDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DecidedPlaceDetailResponse {
    private Long placeId;
    private String title;
    private Long seq;
    private String roadNameAddress;
    private Boolean isDecision;
    private PlaceLikesDto placeLikesDto;



    public DecidedPlaceDetailResponse() {
    }

    @Builder
    public DecidedPlaceDetailResponse(Long placeId, String title, Long seq, String roadNameAddress, Boolean isDecision, PlaceLikesDto placeLikesDto) {
        this.placeId = placeId;
        this.title = title;
        this.seq = seq;
        this.roadNameAddress = roadNameAddress;
        this.isDecision = isDecision;
        this.placeLikesDto = placeLikesDto;
    }
}
