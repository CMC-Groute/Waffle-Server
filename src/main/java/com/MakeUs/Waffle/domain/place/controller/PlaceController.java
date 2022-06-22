package com.MakeUs.Waffle.domain.place.controller;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.place.dto.CreatePlaceRequest;
import com.MakeUs.Waffle.domain.place.service.PlaceService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    //@Operation(summary = "장소 생성 ")
    @PostMapping("/invitations/{invitationId}/placeCategory/{id}/place")
    public ResponseEntity<Long> singUp(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("id") Long categoryId,
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody CreatePlaceRequest createPlaceRequest
    ) {
        return ResponseEntity.ok(placeService.addPlace(token.getId(),invitationId,categoryId,createPlaceRequest));
    }

    //@Operation("summary = "장소 확정하기")
    @PutMapping("/invitations/{invitationId}/place/{id}")
    public ResponseEntity<Long> decidePlace(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("id") Long placeId,
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(placeService.decidePlace(token.getId(),placeId,invitationId));

    }

}
