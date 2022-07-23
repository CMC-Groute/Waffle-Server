package com.MakeUs.Waffle.domain.invitationPlaceCategory.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePlaceCategoryResponse {
    Long placeCategoryId;
    String placeCategoryName;

    @Builder
    public CreatePlaceCategoryResponse(Long placeCategoryId, String placeCategoryName) {
        this.placeCategoryId = placeCategoryId;
        this.placeCategoryName = placeCategoryName;
    }

    public CreatePlaceCategoryResponse() {
    }
}
