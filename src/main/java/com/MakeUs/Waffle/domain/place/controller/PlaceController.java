package com.MakeUs.Waffle.domain.place.controller;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.place.dto.CreatePlaceRequest;
import com.MakeUs.Waffle.domain.place.service.PlaceService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    //@Operation(summary = "장소 생성 ")
    @PostMapping("/invitations/{invitationId}/placeCategory/{id}")
    public ResponseEntity<Long> singUp(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("id") Long categoryId,
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody CreatePlaceRequest createPlaceRequest
    ) {
        return ResponseEntity.ok(placeService.addPlace(token.getId(),invitationId,categoryId,createPlaceRequest));
    }

}
