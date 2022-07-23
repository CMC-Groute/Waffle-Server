package com.MakeUs.Waffle.domain.place.dto;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.PlaceCategoryDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceDetailResponse {
    private String link;
    private String comment;
    PlaceCategoryDto placeCategoryDto;

    public PlaceDetailResponse() {
    }

    @Builder
    public PlaceDetailResponse(String link, String comment, PlaceCategoryDto placeCategoryDto) {
        this.link = link;
        this.comment = comment;
        this.placeCategoryDto = placeCategoryDto;
    }
}
