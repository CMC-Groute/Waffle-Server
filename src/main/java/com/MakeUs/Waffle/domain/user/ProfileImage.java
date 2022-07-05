package com.MakeUs.Waffle.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileImage {
    WAFFLE("WAFFLE"),
    CHOCO("CHOCO"),
    BLUEBERRY("BLUEBERRY"),
    VANILLA("VANILLA"),
    STRAWBERRY("STRAWBERRY"),
    MALCHA("MALCHA");

    private String profileImage;

    ProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
