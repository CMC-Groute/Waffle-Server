package com.MakeUs.Waffle.domain.place;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceCategory {

    LUNCH("LUNCH"),
    CAFE("CAFE"),
    DINNER("DINNER");

    private String PlaceCategory;

    PlaceCategory(String PlaceCategory) {
        this.PlaceCategory = PlaceCategory;
    }
}
