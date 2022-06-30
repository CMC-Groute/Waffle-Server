package com.MakeUs.Waffle.domain.invitation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationCreateRequest {
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime time;

    private String comment;

    private String invitationPlace;

    @Builder
    public InvitationCreateRequest(String title, LocalDate date, LocalTime time, String comment, String invitationPlace) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
    }
}
