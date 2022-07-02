package com.MakeUs.Waffle.domain.place.dto;

import lombok.*;

import java.sql.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchPlaceRequest {
    int currentPage = 1;
    int pageSize = 10;
    String keyword;

    @Builder
    public SearchPlaceRequest(int currentPage, int pageSize, String keyword) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.keyword = keyword;
    }
}
