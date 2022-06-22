package com.MakeUs.Waffle.domain.invitationPlaceCategory.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePlaceCategoryResponse {
    Long placeCategoryId;
    String name;

    @Builder
    public CreatePlaceCategoryResponse(Long placeCategoryId, String name) {
        this.placeCategoryId = placeCategoryId;
        this.name = name;
    }

    public CreatePlaceCategoryResponse() {
    }
}
