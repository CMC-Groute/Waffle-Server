package com.MakeUs.Waffle.domain.invitation.dto;

import com.MakeUs.Waffle.domain.invationMember.dto.InvitationMemberDto;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.PlaceCategoryDto;
import com.MakeUs.Waffle.domain.place.dto.DecidedPlaceDetailResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationDetailResponse {
    private String title;
    private LocalDateTime date;
    private String comment;
    private String invitationPlace;
    private Long waffleId;
    private String invitationImageCategory;
    List<InvitationMemberDto> invitationMemberDto;
    List<PlaceCategoryDto> placeCategoryDto;
    List<DecidedPlaceDetailResponse> decidedPlaceDetailResponses;

    @Builder
    public InvitationDetailResponse(String title, LocalDateTime date, String comment, String invitationPlace,
                                    Long waffleId, List<InvitationMemberDto> invitationMemberDto,
                                    List<PlaceCategoryDto> placeCategoryDto,
                                    List<DecidedPlaceDetailResponse> decidedPlaceDetailResponses,String invitationImageCategory) {
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
        this.waffleId = waffleId;
        this.invitationMemberDto = invitationMemberDto;
        this.placeCategoryDto = placeCategoryDto;
        this.decidedPlaceDetailResponses = decidedPlaceDetailResponses;
        this.invitationImageCategory = invitationImageCategory;
    }
}
