package com.MakeUs.Waffle.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceByCategoryResponse {
    private String title;
    private String roadNameAddress;
    private Boolean isDecision;
    //좋아요


    public PlaceByCategoryResponse() {
    }
    @Builder
    public PlaceByCategoryResponse(String title, String roadNameAddress, Boolean isDecision) {
        this.title = title;
        this.roadNameAddress = roadNameAddress;
        this.isDecision = isDecision;
    }
}
