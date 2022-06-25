package com.MakeUs.Waffle.domain.invitation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationCodeRequest {
    private String invitationCode;

    public InvitationCodeRequest(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
