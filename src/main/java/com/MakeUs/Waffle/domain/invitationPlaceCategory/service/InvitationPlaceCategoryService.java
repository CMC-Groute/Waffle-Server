package com.MakeUs.Waffle.domain.invitationPlaceCategory.service;

import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.PlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.repository.InvitationPlaceCategoryRepository;
import com.MakeUs.Waffle.domain.place.repository.PlaceRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.DuplicatedResourceException;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvitationPlaceCategoryService {

    private final InvitationPlaceCategoryRepository invitationPlaceCategoryRepository;
    private final InvitationMemberRepository invitationMemberRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final PlaceRepository placeRepository;

    @Builder
    public InvitationPlaceCategoryService(InvitationPlaceCategoryRepository invitationPlaceCategoryRepository,
                                          InvitationMemberRepository invitationMemberRepository,
                                          UserRepository userRepository,
                                          InvitationRepository invitationRepository,
                                          PlaceRepository placeRepository) {
        this.invitationPlaceCategoryRepository = invitationPlaceCategoryRepository;
        this.invitationMemberRepository = invitationMemberRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.placeRepository = placeRepository;
    }

    @Transactional
    public List<CreatePlaceCategoryResponse> addInvitationPlaceCategory(Long userId, Long invitationId, CreatePlaceCategoryRequest createPlaceCategoryRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));

        List<CreatePlaceCategoryResponse> createPlaceCategoryResponses = new ArrayList<>();
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));
        for (PlaceCategory placeCategory : createPlaceCategoryRequest.getEnumPlaceCategory(createPlaceCategoryRequest.getPlaceCategories())) {
            if(invitationPlaceCategoryRepository.getByInvitationAndPlaceCategory(invitation,placeCategory)!= null){
                throw new DuplicatedResourceException(ErrorCode.DUPLICATE_PLACE_CATEGORY);
            }
            InvitationPlaceCategory invitationPlaceCategory = InvitationPlaceCategory.builder().placeCategory(placeCategory).invitation(invitation).build();
            InvitationPlaceCategory save = invitationPlaceCategoryRepository.save(invitationPlaceCategory);
            createPlaceCategoryResponses.add(save.toCreatePlaceCategoryResponse());
        }

        return createPlaceCategoryResponses;
    }

    @Transactional
    public void deleteInvitationPlaceCategory(Long userId, Long invitationId, Long  placeCategoryId) {
        invitationMemberRepository.findByUserIdAndInvitationId(userId,invitationId)
                .orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));

        invitationPlaceCategoryRepository.deleteById(placeCategoryId);
        placeRepository.deleteByPlaceCategoryId(placeCategoryId);
    }
}
