package com.MakeUs.Waffle.domain.invationMember.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationMemberDto {
    private Long userId;
    private String nickname;

    @Builder
    public InvitationMemberDto(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
}
