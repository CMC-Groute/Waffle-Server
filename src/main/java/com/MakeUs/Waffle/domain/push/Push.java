package com.MakeUs.Waffle.domain.push;

import com.MakeUs.Waffle.domain.invitation.InvitationImageCategory;
import com.MakeUs.Waffle.domain.push.dto.getPushResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "push")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Push {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long invitationId;

    @Enumerated(EnumType.STRING)
    private InvitationImageCategory invitationImageCategory;

    @Column(nullable = false, columnDefinition = "TINYINT default false")
    private boolean isRead;

    private Long userId;

    @Column(name = "invitation_title", length = 20)
    private String invitationTitle;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private PushType pushType;

    @Builder
    public Push(Long id, Long invitationId, InvitationImageCategory invitationImageCategory, boolean isRead, Long userId, String invitationTitle, String nickName, PushType pushType) {
        this.id = id;
        this.invitationId = invitationId;
        this.invitationImageCategory = invitationImageCategory;
        this.isRead = isRead;
        this.userId = userId;
        this.invitationTitle = invitationTitle;
        this.nickName = nickName;
        this.pushType = pushType;
    }


    public getPushResponse toGetPushResponse(){
        return getPushResponse.builder()
                .id(id)
                .invitationImageCategory(invitationImageCategory)
                .invitationId(invitationId)
                .pushType(pushType)
                .nickName(nickName)
                .invitationTitle(invitationTitle)
                .isRead(isRead)
                .build();
    }

    public void updateIsRead(boolean isRead){
        this.isRead = isRead;
    }
}
