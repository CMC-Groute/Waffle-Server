package com.MakeUs.Waffle.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileImage {
    INITIAL("INITIAL"),
    TWO("TWO");

    private String profileImage;

    ProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
