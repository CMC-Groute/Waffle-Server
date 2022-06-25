package com.MakeUs.Waffle.domain.invitation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationCreateRequest {
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;

    private String comment;

    private String invitationPlace;

    public InvitationCreateRequest(String title, LocalDateTime date, String comment, String invitationPlace) {
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
    }
}
