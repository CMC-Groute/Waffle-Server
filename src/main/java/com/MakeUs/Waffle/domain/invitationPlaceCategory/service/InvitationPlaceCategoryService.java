package com.MakeUs.Waffle.domain.invitationPlaceCategory.service;

import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.PlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.repository.InvitationPlaceCategoryRepository;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.DuplicatedResourceException;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvitationPlaceCategoryService {

    private final InvitationPlaceCategoryRepository invitationPlaceCategoryRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    public InvitationPlaceCategoryService(InvitationPlaceCategoryRepository invitationPlaceCategoryRepository, UserRepository userRepository, InvitationRepository invitationRepository) {
        this.invitationPlaceCategoryRepository = invitationPlaceCategoryRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
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
}
