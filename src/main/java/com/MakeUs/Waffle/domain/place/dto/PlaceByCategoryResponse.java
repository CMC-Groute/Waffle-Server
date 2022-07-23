package com.MakeUs.Waffle.domain.place.dto;

import com.MakeUs.Waffle.domain.placeLikes.dto.PlaceLikesDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceByCategoryResponse {
    private Long placeId;
    private String title;
    private String roadNameAddress;
    private Boolean isDecision;
    private PlaceLikesDto placeLikesDto;
    private Long seq;
    private String longitude;
    private String latitude;


    public PlaceByCategoryResponse() {
    }

    @Builder
    public PlaceByCategoryResponse(Long placeId, String title, String roadNameAddress, Boolean isDecision, PlaceLikesDto placeLikesDto, Long seq, String longitude, String latitude) {
        this.placeId = placeId;
        this.title = title;
        this.roadNameAddress = roadNameAddress;
        this.isDecision = isDecision;
        this.placeLikesDto = placeLikesDto;
        this.seq = seq;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
