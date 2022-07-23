package com.MakeUs.Waffle.domain.push;

import lombok.Getter;

@Getter
public enum PushType {
    ALARM_LIKES("ALARM_LIKES","좋아요 조르기"),
    ALARM_JOIN("ALARM_JOIN","초대"),
    ALARM_NOT_DECIDE("ALARM_NOT_DECIDE","미정"),
    ALARM_BEFORE_DAY("ALARM_BEFORE_DAY","약속전");


    private final String code;

    private final String codeName;

    PushType(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
}
