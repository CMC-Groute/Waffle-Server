package com.MakeUs.Waffle.domain.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateIsAgreedAlarm {
    private Boolean isAgreedAlarm;

    @Builder
    public UserUpdateIsAgreedAlarm(Boolean isAgreedAlarm) {
        this.isAgreedAlarm = isAgreedAlarm;
    }
}
