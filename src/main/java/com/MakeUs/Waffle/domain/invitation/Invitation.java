package com.MakeUs.Waffle.domain.invitation;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.dto.InvitationMemberDto;

import com.MakeUs.Waffle.domain.invitation.dto.InvitationDetailResponse;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationListResponse;

import com.MakeUs.Waffle.domain.invitation.dto.InvitationUpdateRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.PlaceCategoryDto;
import com.MakeUs.Waffle.domain.place.Place;
import com.MakeUs.Waffle.domain.place.dto.DecidedPlaceDetailResponse;
import com.MakeUs.Waffle.domain.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    private LocalDate date;

    private LocalTime time;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private String invitationCode;

    private String invitationPlace;

    private Long organizerId;

    @Enumerated(EnumType.STRING)
    private InvitationImageCategory invitationImageCategory;

    @OneToMany(mappedBy = "invitation", orphanRemoval = true)
    private List<InvitationMember> invitationMembers;

    @Column(name = "is_expired", nullable = false, columnDefinition = "TINYINT default 1")
    private boolean isExpired;

    @OneToMany(mappedBy = "invitation", orphanRemoval = true)
    private List<InvitationPlaceCategory> invitationPlaceCategories;

    @OneToMany(mappedBy = "invitation", orphanRemoval = true)
    private List<Place> places;

    @Builder
    public Invitation(Long id, String title, LocalDate date, LocalTime time, String comment, String invitationCode, String invitationPlace, Long organizerId, InvitationImageCategory invitationImageCategory, List<InvitationMember> invitationMembers, boolean isExpired, List<InvitationPlaceCategory> invitationPlaceCategories, List<Place> places) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.invitationCode = invitationCode;
        this.invitationPlace = invitationPlace;
        this.organizerId = organizerId;
        this.invitationImageCategory = invitationImageCategory;
        this.invitationMembers = invitationMembers;
        this.isExpired = isExpired;
        this.invitationPlaceCategories = invitationPlaceCategories;
        this.places = places;
    }


    public void addInvitationMember(InvitationMember invitationMember) {
        this.invitationMembers.add(invitationMember);
        invitationMember.setInvitation(this);
    }

    public Invitation addInvitationMembers(List<InvitationMember> invitationMembers) {
        invitationMembers.forEach(this::addInvitationMember);
        return this;
    }

    public void addPlace(Place place) {
        this.places.add(place);
        place.setInvitation(this);
    }

    public Invitation addPlaces(List<Place> places) {
        places.forEach(this::addPlace);
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

    public void updateInvitation(InvitationUpdateRequest invitationUpdateRequest){
        this.title = invitationUpdateRequest.getTitle();
        this.invitationPlace = invitationUpdateRequest.getInvitationPlace();
        this.comment = invitationUpdateRequest.getComment();
        this.date = invitationUpdateRequest.getDate();
        this.time = invitationUpdateRequest.getTime();
    }

    public InvitationListResponse toInvitationListResponse(List<InvitationMemberDto> invitationMemberDto) {
        return InvitationListResponse.builder()
                .invitationMemberDto(invitationMemberDto)
                .invitationId(id)
                .waffleId(organizerId)
                .invitationImageCategory(invitationImageCategory.toString())
                .invitationPlace(invitationPlace)
                .comment(comment)
                .date(date)
                .time(time)
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
                .time(time)
                .invitationImageCategory(invitationImageCategory.toString())
                .decidedPlaceDetailResponses(decidedPlaceDetailResponses)
                .placeCategoryDto(placeCategoryDtos)
                .comment(comment)
                .waffleId(organizerId)
                .invitationPlace(invitationPlace)
                .build();
    }

}
