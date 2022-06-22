package com.MakeUs.Waffle.domain.invitationPlaceCategory.controller;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.service.InvitationPlaceCategoryService;
import com.MakeUs.Waffle.domain.user.dto.UserSignUpRequest;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class InvitationPlaceCategoryController {
    private final InvitationPlaceCategoryService invitationPlaceCategoryService;

    public InvitationPlaceCategoryController(InvitationPlaceCategoryService invitationPlaceCategoryService) {
        this.invitationPlaceCategoryService = invitationPlaceCategoryService;
    }

    //@Operation(summary = "회원가입 컨트롤러")
    @PostMapping("/invitations/{id}/placeCategory")
    public ResponseEntity<List<CreatePlaceCategoryResponse>> singUp(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody CreatePlaceCategoryRequest createPlaceCategoryRequest
    ) {
        return ResponseEntity.ok(invitationPlaceCategoryService.addInvitationPlaceCategory(token.getId(),id,createPlaceCategoryRequest));
    }
}
