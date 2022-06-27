package com.MakeUs.Waffle.domain.invitation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvitationImageCategory {
    CHOCO("CHOCO"),
    BLUEBERRY("BLUEBERRY"),
    VANILA("VANILA"),
    STRAWBERRY("STRAWBERRY"),
    MALCHA("MALCHA");

    private String invitationImageCategory;

    InvitationImageCategory(String invitationImageCategory) {
        this.invitationImageCategory = invitationImageCategory;
    }
}
