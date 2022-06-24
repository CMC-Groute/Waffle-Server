package com.MakeUs.Waffle.domain.invitationPlaceCategory.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceCategoryDto {
    Long placeCategoryId;
    String placeCategoryName;

    @Builder
    public PlaceCategoryDto(Long placeCategoryId, String placeCategoryName) {
        this.placeCategoryId = placeCategoryId;
        this.placeCategoryName = placeCategoryName;
    }
}
