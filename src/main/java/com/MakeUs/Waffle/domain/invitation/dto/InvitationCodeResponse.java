package com.MakeUs.Waffle.domain.invitation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InvitationCodeResponse {
    private String code;

    @Builder
    public InvitationCodeResponse(String code) {
        this.code = code;
    }

    public InvitationCodeResponse() {
    }
}
