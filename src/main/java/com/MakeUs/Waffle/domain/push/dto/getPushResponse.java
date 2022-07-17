package com.MakeUs.Waffle.domain.push.dto;

import com.MakeUs.Waffle.domain.push.PushType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class getPushResponse {

    private Long invitationId;
    private String invitationTitle;
    private String nickName;
    private PushType pushType;

    @Builder
    public getPushResponse(Long invitationId, String invitationTitle, String nickName, PushType pushType) {
        this.invitationId = invitationId;
        this.invitationTitle = invitationTitle;
        this.nickName = nickName;
        this.pushType = pushType;
    }
}
