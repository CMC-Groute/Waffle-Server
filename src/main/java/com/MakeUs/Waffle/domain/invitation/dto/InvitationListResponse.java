package com.MakeUs.Waffle.domain.invitation.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationListResponse {
    private String title;

    private LocalDateTime date;

    private String comment;

    private String invitationPlace;

    @Builder
    public InvitationListResponse(String title, LocalDateTime date, String comment, String invitationPlace) {
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
    }

}
