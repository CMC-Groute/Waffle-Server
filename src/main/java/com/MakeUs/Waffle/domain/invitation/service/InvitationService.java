package com.MakeUs.Waffle.domain.invitation.service;

import com.MakeUs.Waffle.domain.invationMember.InvitationMember;
import com.MakeUs.Waffle.domain.invationMember.dto.InvitationMemberDto;
import com.MakeUs.Waffle.domain.invationMember.repository.InvitationMemberRepository;
import com.MakeUs.Waffle.domain.invitation.Invitation;
import com.MakeUs.Waffle.domain.invitation.InvitationImageCategory;
import com.MakeUs.Waffle.domain.invitation.dto.*;
import com.MakeUs.Waffle.domain.invitation.repository.InvitationRepository;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.InvitationPlaceCategory;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.PlaceCategoryDto;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.repository.InvitationPlaceCategoryRepository;
import com.MakeUs.Waffle.domain.place.dto.DecidedPlaceDetailResponse;
import com.MakeUs.Waffle.domain.place.service.PlaceService;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@Getter
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final InvitationMemberRepository invitationMemberRepository;
    private final InvitationPlaceCategoryRepository invitationPlaceCategoryRepository;
    private final PlaceService placeService;

    public InvitationService(InvitationRepository invitationRepository, UserRepository userRepository,
                             InvitationMemberRepository invitationMemberRepository,
                             InvitationPlaceCategoryRepository invitationPlaceCategoryRepository,
                             PlaceService placeService) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.invitationMemberRepository = invitationMemberRepository;
        this.invitationPlaceCategoryRepository = invitationPlaceCategoryRepository;
        this.placeService = placeService;
    }

    @Transactional
    public Long createInvitation(Long userId, InvitationCreateRequest invitationCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        InvitationImageCategory invitationImageCategory = InvitationImageCategory.valueOf(getInvitationImageCategory());
        Invitation invitation = Invitation.builder()
                .invitationCode(this.getInvitationCode())
                .comment(invitationCreateRequest.getComment())
                .date(invitationCreateRequest.getDate())
                .invitationPlace(invitationCreateRequest.getInvitationPlace())
                .title(invitationCreateRequest.getTitle())
                .invitationImageCategory(invitationImageCategory)
                .organizerId(userId)
                .build();

        InvitationMember invitationMember = InvitationMember.builder()
                .invitation(invitation)
                .user(user)
                .build();

        invitationMemberRepository.save(invitationMember);
        return invitationRepository.save(invitation).getId();
    }

    public String getInvitationCode() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public String getInvitationImageCategory(){
        String[] stringSet = new String[]{"CHOCO", "BLUEBERRY", "VANILA","STRAWBERRY", "MALCHA"};
        int idx = (int) (5 * Math.random());
        return stringSet[idx];
    }

    @Transactional
    public Long inviteInvitation(Long userId, InvitationCodeRequest invitationCodeRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));

        Invitation invitation = invitationRepository.findByInvitationCode(invitationCodeRequest.getInvitationCode())
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_MATCH_INVITATION_CODE));

        InvitationMember invitationMember = InvitationMember.builder()
                .invitation(invitation)
                .user(user)
                .build();

        return invitationMemberRepository.save(invitationMember).getId();
    }

    @Transactional(readOnly = true)
    public InvitationCodeResponse getInvitationCode(Long userId, Long invitationId){
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));
        InvitationMember invitationMember = invitationMemberRepository.findByUserIdAndInvitationId(userId,invitationId)
                .orElseThrow(() -> new NotMatchResourceException(ErrorCode.NOT_MATCH_INVITATION_MEMBER));

        return InvitationCodeResponse.builder()
                .code(invitation.getInvitationCode())
                .build();
    }

    @Transactional(readOnly = true)
    public List<InvitationListResponse> findInvitationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));

        List<Invitation> invitations = invitationRepository.getByUser(userId);
        return invitations.stream().map(Invitation::toInvitationListResponse).collect(toList());
    }

    @Transactional(readOnly = true)
    public InvitationDetailResponse getDetailInvitation(Long userId, Long invitationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION));

        List<InvitationMember> invitationMembers = invitationMemberRepository.findByInvitation(invitation).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_INVITATION_MEMBER));
        List<InvitationMemberDto> invitationMemberDtos = invitationMembers.stream().map(InvitationMember::toInvitationMemberDto).collect(toList());

        List<InvitationPlaceCategory> invitationPlaceCategories = invitationPlaceCategoryRepository.getByInvitationId(invitationId);
        List<PlaceCategoryDto> placeCategoryDtos = invitationPlaceCategories.stream().map(InvitationPlaceCategory::toPlaceCategoryDto).collect(toList());

        List<DecidedPlaceDetailResponse> decidedPlace = placeService.getDecidedPlace(userId, invitationId);

        return invitation.toInvitationDetailResponse(invitationMemberDtos,decidedPlace,placeCategoryDtos);

    }
}
