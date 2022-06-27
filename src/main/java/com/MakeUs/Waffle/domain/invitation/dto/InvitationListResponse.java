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

    private String InvitationImageCategory;

    @Builder
    public InvitationListResponse(String title, LocalDateTime date, String comment, String invitationPlace, String invitationImageCategory) {
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
        InvitationImageCategory = invitationImageCategory;
    }
}
