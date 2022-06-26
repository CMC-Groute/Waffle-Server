package com.MakeUs.Waffle.domain.invitation.controller;

import com.MakeUs.Waffle.domain.invitation.dto.InvitationCodeRequest;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationCreateRequest;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationDetailResponse;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationListResponse;
import com.MakeUs.Waffle.domain.invitation.service.InvitationService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }


    @PostMapping("/invitations")
    public ResponseEntity<ApiResponse<Long>> createInvitation(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody InvitationCreateRequest invitationCreateRequest
    ) {
        return ResponseEntity.ok(ApiResponse.of(invitationService.createInvitation(token.getId(),invitationCreateRequest)));
    }

    @PostMapping("/invitations/code")
    public ResponseEntity<ApiResponse<Long>> inviteInvitation(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody InvitationCodeRequest invitationCodeRequest
    ) {
        return ResponseEntity.ok(ApiResponse.of(invitationService.inviteInvitation(token.getId(),invitationCodeRequest)));
    }

    //사용자 예정된 약속들 확인
    @GetMapping("/invitations")
    public ResponseEntity<ApiResponse<List<InvitationListResponse>>> findInvitationByUser(
            @AuthenticationPrincipal JwtAuthentication token
    ) {
        return ResponseEntity.ok(ApiResponse.of(invitationService.findInvitationsByUser(token.getId())));
    }

    //약속 상세조회
    @GetMapping("/invitations/{invitationId}")
    public ResponseEntity<ApiResponse<InvitationDetailResponse>> findDetailInvitation(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("invitationId") Long invitationId
            ) {
        return ResponseEntity.ok(ApiResponse.of(invitationService.getDetailInvitation(token.getId(),invitationId)));
    }
}
