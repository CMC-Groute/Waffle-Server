package com.MakeUs.Waffle.domain.invitationPlaceCategory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlaceCategory {
    FOOD("FOOD"),
    CAFE("CAFE"),
    BREAKFAST("BREAKFAST"),
    LUNCH("LUNCH"),
    DINNER("DINNER"),
    PROP_SHOP("PROP_SHOP"),
    TODO("TODO"),
    TO_SEE("TO_SEE"),
    ETC("ETC");


    private String placeCategory;

    PlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }
}
