package com.MakeUs.Waffle.domain.invitationPlaceCategory.dto;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.PlaceCategory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreatePlaceCategoryRequest {
    private List<String> placeCategories;

    public CreatePlaceCategoryRequest() {
    }

    public List<PlaceCategory> getEnumPlaceCategory(List<String> placeCategories) {
        List<PlaceCategory> result = new ArrayList<>();
        for (String placeCategory : placeCategories) {
            result.add(PlaceCategory.valueOf(placeCategory));
        }
        return result;
    }
}
