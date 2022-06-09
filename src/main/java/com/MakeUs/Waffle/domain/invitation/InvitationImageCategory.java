package com.MakeUs.Waffle.domain.invitation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvitationImageCategory {
    CHERRY("CHERRY"),
    BANANA("BANANA"),
    MINT("MINT");

    private String invitationImageCategory;

    InvitationImageCategory(String invitationImageCategory) {
        this.invitationImageCategory = invitationImageCategory;
    }
}
