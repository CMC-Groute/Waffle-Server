package com.MakeUs.Waffle.domain.invitation.dto;

import com.MakeUs.Waffle.domain.invationMember.dto.InvitationMemberDto;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.PlaceCategoryDto;
import com.MakeUs.Waffle.domain.place.dto.DecidedPlaceDetailResponse;
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
public class InvitationDetailResponse {
    private String title;
    @JsonFormat(pattern = "MM월 dd일", timezone = "Asia/Seoul")
    private LocalDate date;
    @JsonFormat(pattern = "a h시 m분", timezone = "Asia/Seoul")
    private LocalTime time;
    private String comment;
    private String invitationPlace;
    private Long waffleId;
    private String invitationImageCategory;
    List<InvitationMemberDto> invitationMemberDto;
    List<PlaceCategoryDto> placeCategoryDto;
    List<DecidedPlaceDetailResponse> decidedPlaceDetailResponses;

    @Builder
    public InvitationDetailResponse(String title, LocalDate date, LocalTime time, String comment, String invitationPlace, Long waffleId, String invitationImageCategory, List<InvitationMemberDto> invitationMemberDto, List<PlaceCategoryDto> placeCategoryDto, List<DecidedPlaceDetailResponse> decidedPlaceDetailResponses) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
        this.waffleId = waffleId;
        this.invitationImageCategory = invitationImageCategory;
        this.invitationMemberDto = invitationMemberDto;
        this.placeCategoryDto = placeCategoryDto;
        this.decidedPlaceDetailResponses = decidedPlaceDetailResponses;
    }
}
