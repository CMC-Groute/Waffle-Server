package com.MakeUs.Waffle.domain.invitation.dto;

import com.MakeUs.Waffle.domain.invationMember.dto.InvitationMemberDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationListResponse {
    private Long invitationId;
    private String title;
    private LocalDateTime date;
    private String comment;
    private String invitationPlace;
    private String InvitationImageCategory;
    private Long waffleId;
    List<InvitationMemberDto> invitationMemberDto;

    @Builder
    public InvitationListResponse(Long invitationId, String title, LocalDateTime date, String comment, String invitationPlace, String invitationImageCategory, Long waffleId, List<InvitationMemberDto> invitationMemberDto) {
        this.invitationId = invitationId;
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.invitationPlace = invitationPlace;
        InvitationImageCategory = invitationImageCategory;
        this.waffleId = waffleId;
        this.invitationMemberDto = invitationMemberDto;
    }
}
