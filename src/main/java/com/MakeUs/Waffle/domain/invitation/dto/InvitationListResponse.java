package com.MakeUs.Waffle.domain.invitation.dto;

import com.MakeUs.Waffle.domain.invationMember.dto.InvitationMemberDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationListResponse {
    private Long invitationId;
    private String title;
    @JsonFormat(pattern = "MM월 dd일", timezone = "Asia/Seoul")
    private LocalDate date;
    @JsonFormat(pattern = "a h시 m분", timezone = "Asia/Seoul")
    private LocalTime time;

    private String comment;
    private String invitationPlace;
    private String InvitationImageCategory;
    private Long waffleId;
    List<InvitationMemberDto> invitationMemberDto;


    @Builder
    public InvitationListResponse(Long invitationId, String title, LocalDate date, LocalTime time, String comment, String invitationPlace, String invitationImageCategory, Long waffleId, List<InvitationMemberDto> invitationMemberDto) {
        this.invitationId = invitationId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
        InvitationImageCategory = invitationImageCategory;
        this.waffleId = waffleId;
        this.invitationMemberDto = invitationMemberDto;
    }
}
