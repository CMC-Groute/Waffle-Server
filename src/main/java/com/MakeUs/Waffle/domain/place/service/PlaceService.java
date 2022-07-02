package com.MakeUs.Waffle.domain.place.service;

import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.repository.InvitationPlaceCategoryRepository;
import com.MakeUs.Waffle.domain.place.Place;
import com.MakeUs.Waffle.domain.place.dto.*;
import com.MakeUs.Waffle.domain.place.repository.PlaceRepository;
import com.MakeUs.Waffle.domain.placeLikes.repository.PlaceLikesRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final InvitationPlaceCategoryRepository invitationPlaceCategoryRepository;
    private final InvitationMemberRepository invitationMemberRepository;
    private final InvitationRepository invitationRepository;
    private final PlaceLikesRepository placeLikesRepository;

    public PlaceService(PlaceRepository placeRepository, UserRepository userRepository, InvitationPlaceCategoryRepository invitationPlaceCategoryRepository, InvitationMemberRepository invitationMemberRepository, InvitationRepository invitationRepository, PlaceLikesRepository placeLikesRepository) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.invitationPlaceCategoryRepository = invitationPlaceCategoryRepository;
        this.invitationMemberRepository = invitationMemberRepository;
        this.invitationRepository = invitationRepository;
        this.placeLikesRepository = placeLikesRepository;
    }


    @Transactional
    public Long addPlace(Long userId, Long invitationId, Long categoryId, CreatePlaceRequest createPlaceRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));

        return placeRepository.save(createPlaceRequest.toEntity(categoryId, invitation)).getId();
    }

    public boolean userValid(final Long userId, final Long compareUserId) {
        if (!userId.equals(compareUserId)) {
            throw new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER);
        }
        return false;
    }

    @Transactional
    public Long decidePlace(Long userId, Long placeId, Long invitationId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));
        invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));
        List<Place> decidedPlace = placeRepository.getByInvitationIdAndIsDecisionTrue(invitationId);
        place.decidePlace((long) (decidedPlace.size() + 1));
        return placeId;
    }

    @Transactional
    public Long cancelDecidePlace(Long userId, Long placeId, Long invitationId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));
        invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));

        place.cancelDecidePlace();
        return placeId;
    }

    @Transactional
    public List<DecidedPlaceDetailResponse> getDecidedPlace(Long userId, Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));
        invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));

        List<Place> places = placeRepository.getByInvitationAndIsDecisionTrueOrderBySeq(invitation);
        return places.stream().map(Place::toDecidedPlaceDetailResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<DecidedPlaceDetailResponse> updateDecidedPlaceSeq(
            Long userId, Long invitationId, UpdateDecidePlaceRequest updateDecidePlaceRequest
    ) {
        invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));
        List<Place> places = new ArrayList<>();
        for (PlaceSeqDto placeSeqDto : updateDecidePlaceRequest.getPlaceSeqDtos()) {
            Place place = placeRepository.findById(placeSeqDto.getPlaceId()).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));
            place.updateSeq(placeSeqDto.getSeq());
            places.add(place);
        }

        return places.stream()
                .map(Place::toDecidedPlaceDetailResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PlaceByCategoryResponse> findPlaceByCategory(Long userId, Long invitationId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));
        InvitationPlaceCategory invitationPlaceCategory = invitationPlaceCategoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE_CATEGORY));

        List<Place> places = placeRepository.getByInvitationAndPlaceCategoryIdOrderByCreatedAt(invitation, categoryId);
        List<PlaceByCategoryResponse> placeByCategoryResponses = new ArrayList<>();
        for(Place place : places){
            if(placeLikesRepository.existsByUserIdAndPlace(userId,place)){
                placeByCategoryResponses.add(place.toPlaceByCategoryResponse(true));
            } else{
                placeByCategoryResponses.add(place.toPlaceByCategoryResponse(false));

            }
        }
        return placeByCategoryResponses;
    }

    @Transactional
    public PlaceDetailResponse getDetailPlace(Long userId,Long invitationId, Long placeId){
        invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));
        return place.toPlaceDetailResponse();
    }

    @Transactional
    public Long updatePlace(Long userId,Long invitationId, Long placeId, UpdatePlaceRequest updatePlaceRequest){
        invitationMemberRepository.findByUserIdAndInvitationId(userId, invitationId).orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_PLACE));
        place.updatePlaceInfo(updatePlaceRequest);
        return placeId;
    }
}
