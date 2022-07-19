package com.MakeUs.Waffle.domain.push.dto;

import com.MakeUs.Waffle.domain.invitation.InvitationImageCategory;
import com.MakeUs.Waffle.domain.push.PushType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class getPushResponse {
    private Long id;
    private Long invitationId;
    private String invitationTitle;
    private String nickName;
    private PushType pushType;
    private InvitationImageCategory invitationImageCategory;
    @JsonFormat(pattern = "YYYY-MM월-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    private boolean isRead;

    @Builder
    public getPushResponse(Long id, Long invitationId, String invitationTitle, String nickName, PushType pushType, InvitationImageCategory invitationImageCategory, LocalDateTime createAt, boolean isRead) {
        this.id = id;
        this.invitationId = invitationId;
        this.invitationTitle = invitationTitle;
        this.nickName = nickName;
        this.pushType = pushType;
        this.invitationImageCategory = invitationImageCategory;
        this.createAt = createAt;
        this.isRead = isRead;
    }
}
