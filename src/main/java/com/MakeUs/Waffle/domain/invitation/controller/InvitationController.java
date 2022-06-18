package com.MakeUs.Waffle.domain.invitation.controller;

import com.MakeUs.Waffle.domain.invitation.dto.InvitationCodeRequest;
import com.MakeUs.Waffle.domain.invitation.dto.InvitationCreateRequest;
import com.MakeUs.Waffle.domain.invitation.service.InvitationService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }


    @PostMapping("/invitations")
    public ResponseEntity<Long> createInvitation(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody InvitationCreateRequest invitationCreateRequest
    ) {
        return ResponseEntity.ok(invitationService.createInvitation(token.getId(),invitationCreateRequest));
    }

    @PostMapping("/invitations/code")
    public ResponseEntity<Long> inviteInvitation(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody InvitationCodeRequest invitationCodeRequest
    ) {
        return ResponseEntity.ok(invitationService.inviteInvitation(token.getId(),invitationCodeRequest));
    }
}
