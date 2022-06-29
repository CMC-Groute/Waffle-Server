package com.MakeUs.Waffle.domain.invitationPlaceCategory.controller;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.service.InvitationPlaceCategoryService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<List<CreatePlaceCategoryResponse>>> singUp(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody CreatePlaceCategoryRequest createPlaceCategoryRequest
    ) {
        return ResponseEntity.ok(ApiResponse.of(invitationPlaceCategoryService.addInvitationPlaceCategory(token.getId(),id,createPlaceCategoryRequest)));
    }
    //@Operation(summary = "카테고리 삭제 컨트롤러")
    @DeleteMapping("/invitations/{invitationId}/placeCategory/{placeCategoryId}")
    public ResponseEntity<ApiResponse<List<CreatePlaceCategoryResponse>>> deletePlaceCategory(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("placeCategoryId") Long placeCategoryId,
            @AuthenticationPrincipal JwtAuthentication token
    ) {
        invitationPlaceCategoryService.deleteInvitationPlaceCategory(token.getId(),invitationId,placeCategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
