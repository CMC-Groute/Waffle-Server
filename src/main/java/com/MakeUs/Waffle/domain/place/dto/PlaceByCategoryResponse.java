package com.MakeUs.Waffle.domain.place.dto;

import com.MakeUs.Waffle.domain.placeLikes.dto.PlaceLikesDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceByCategoryResponse {
    private String title;
    private String roadNameAddress;
    private Boolean isDecision;
    private PlaceLikesDto placeLikesDto;


    public PlaceByCategoryResponse() {
    }

    @Builder
    public PlaceByCategoryResponse(String title, String roadNameAddress, Boolean isDecision, PlaceLikesDto placeLikesDto) {
        this.title = title;
        this.roadNameAddress = roadNameAddress;
        this.isDecision = isDecision;
        this.placeLikesDto = placeLikesDto;
    }
}
