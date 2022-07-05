package com.MakeUs.Waffle.domain.invationMember.controller;

import com.MakeUs.Waffle.domain.invationMember.service.InvitationMemberService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvitationMemberController {
    private InvitationMemberService invitationMemberService;

    public InvitationMemberController(InvitationMemberService invitationMemberService) {
        this.invitationMemberService = invitationMemberService;
    }

    @DeleteMapping("/invitations/{id}/invitationMember")
    public ResponseEntity<ApiResponse<Long>> exitInvitationMember(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable("id") Long invitationId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                invitationMemberService.exitInvitationMember(token.getId(),invitationId)));
    }
}
