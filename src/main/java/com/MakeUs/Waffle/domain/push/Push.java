package com.MakeUs.Waffle.domain.push;

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

    private Long userId;

    @Column(name = "invitation_title", length = 20)
    private String invitationTitle;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private PushType pushType;

    @Builder
    public Push(Long id, Long invitationId, Long userId, String invitationTitle, String nickName, PushType pushType) {
        this.id = id;
        this.invitationId = invitationId;
        this.userId = userId;
        this.invitationTitle = invitationTitle;
        this.nickName = nickName;
        this.pushType = pushType;
    }

    public getPushResponse toGetPushResponse(){
        return getPushResponse.builder()
                .invitationId(invitationId)
                .pushType(pushType)
                .nickName(nickName)
                .invitationTitle(invitationTitle)
                .build();
    }
}
