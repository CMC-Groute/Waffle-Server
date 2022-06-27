package com.MakeUs.Waffle.domain.invitation;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.dto.InvitationMemberDto;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationDetailResponse;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationListResponse;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.PlaceCategoryDto;
import com.MakeUs.Waffle.domain.place.dto.DecidedPlaceDetailResponse;
import com.MakeUs.Waffle.domain.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Invitation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    private LocalDateTime date;

    private String comment;

    private String invitationCode;

    private String invitationPlace;

    private Long organizerId;

    @Enumerated(EnumType.STRING)
    private InvitationImageCategory invitationImageCategory;

    @OneToMany(mappedBy = "invitation", orphanRemoval = true)
    private List<InvitationMember> invitationMembers;

    @OneToMany(mappedBy = "invitation", orphanRemoval = true)
    private List<InvitationPlaceCategory> invitationPlaceCategories;

    @Builder
    public Invitation(Long id, String title, LocalDateTime date, String comment, String invitationCode, String invitationPlace, Long organizerId, InvitationImageCategory invitationImageCategory, List<InvitationMember> invitationMembers, List<InvitationPlaceCategory> invitationPlaceCategories) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.invitationCode = invitationCode;
        this.invitationPlace = invitationPlace;
        this.organizerId = organizerId;
        this.invitationImageCategory = invitationImageCategory;
        this.invitationMembers = invitationMembers;
        this.invitationPlaceCategories = invitationPlaceCategories;
    }


    public void addInvitationMember(InvitationMember invitationMember) {
        this.invitationMembers.add(invitationMember);
        invitationMember.setInvitation(this);
    }

    public Invitation addInvitationMembers(List<InvitationMember> invitationMembers) {
        invitationMembers.forEach(this::addInvitationMember);
        return this;
    }

    public void addInvitationCategory(InvitationPlaceCategory invitationPlaceCategory) {
        this.invitationPlaceCategories.add(invitationPlaceCategory);
        invitationPlaceCategory.setInvitation(this);
    }

    public Invitation addInvitationCategories(List<InvitationPlaceCategory> invitationPlaceCategories) {
        invitationPlaceCategories.forEach(this::addInvitationCategory);
        return this;
    }

    public InvitationListResponse toInvitationListResponse() {
        return InvitationListResponse.builder()
                .invitationImageCategory(invitationImageCategory.toString())
                .invitationPlace(invitationPlace)
                .comment(comment)
                .date(date)
                .title(title)
                .build();
    }

    public InvitationDetailResponse toInvitationDetailResponse(List<InvitationMemberDto> invitationMemberDto,
                                                               List<DecidedPlaceDetailResponse> decidedPlaceDetailResponses,
                                                               List<PlaceCategoryDto> placeCategoryDtos) {
        return InvitationDetailResponse.builder()
                .title(title)
                .invitationMemberDto(invitationMemberDto)
                .date(date)
                .invitationImageCategory(invitationImageCategory.toString())
                .decidedPlaceDetailResponses(decidedPlaceDetailResponses)
                .placeCategoryDto(placeCategoryDtos)
                .comment(comment)
                .waffleId(organizerId)
                .invitationPlace(invitationPlace)
                .build();
    }

}
