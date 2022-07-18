package com.MakeUs.Waffle.domain.push.dto;

import com.MakeUs.Waffle.domain.invitation.InvitationImageCategory;
import com.MakeUs.Waffle.domain.push.PushType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class getPushResponse {
    private Long id;
    private Long invitationId;
    private String invitationTitle;
    private String nickName;
    private PushType pushType;
    private InvitationImageCategory invitationImageCategory;
    private boolean isRead;

    @Builder
    public getPushResponse(Long id, Long invitationId, String invitationTitle, String nickName, PushType pushType, InvitationImageCategory invitationImageCategory, boolean isRead) {
        this.id = id;
        this.invitationId = invitationId;
        this.invitationTitle = invitationTitle;
        this.nickName = nickName;
        this.pushType = pushType;
        this.invitationImageCategory = invitationImageCategory;
        this.isRead = isRead;
    }
}
