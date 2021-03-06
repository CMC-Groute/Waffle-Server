package com.MakeUs.Waffle.domain.place.controller;

import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryRequest;
import com.MakeUs.Waffle.domain.invitationPlaceCategory.dto.CreatePlaceCategoryResponse;
import com.MakeUs.Waffle.domain.place.dto.*;
import com.MakeUs.Waffle.domain.place.service.PlaceService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    //@Operation(summary = "장소 생성 ")
    @PostMapping("/invitations/{invitationId}/placeCategory/{id}/place")
    public ResponseEntity<ApiResponse<Long>> singUp(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("id") Long categoryId,
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody CreatePlaceRequest createPlaceRequest
    ) {
        return ResponseEntity.ok(ApiResponse.of(placeService.addPlace(token.getId(),invitationId,categoryId,createPlaceRequest)));
    }

    //@Operation("summary = "장소 확정하기")
    @PutMapping("/invitations/{invitationId}/place/{id}/decide")
    public ResponseEntity<ApiResponse<Long>> decidePlace(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("id") Long placeId,
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.decidePlace(token.getId(),placeId,invitationId)));
    }

    //@Operation("summary = "장소 확정 취소하기")
    @PutMapping("/invitations/{invitationId}/place/{id}")
    public ResponseEntity<ApiResponse<Long>> cancelDecidePlace(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("id") Long placeId,
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.cancelDecidePlace(token.getId(),placeId,invitationId)));
    }

    //@Operation("summary = "확정된 장소 조회하기")
    @GetMapping("/invitations/{invitationId}/place")
    public ResponseEntity<ApiResponse<List<DecidedPlaceDetailResponse>>> getDecidedPlace(
            @PathVariable("invitationId") Long invitationId,
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.getDecidedPlace(token.getId(),invitationId)));
    }

    //@Operation("summary = "확정된 장소 순서 변경하기")
    @PutMapping("/invitations/{invitationId}")
    public ResponseEntity<ApiResponse<List<DecidedPlaceDetailResponse>>> updateDecidedPlaceSeq(
            @PathVariable("invitationId") Long invitationId,
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody UpdateDecidePlaceRequest updateDecidePlaceRequest
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.updateDecidedPlaceSeq(token.getId(),invitationId,updateDecidePlaceRequest)));
    }

    //@Operation("summary = "장소카테고리로 장소 조회하기")
    @GetMapping("/invitations/{invitationId}/placeCategory/{categoryId}/place")
    public ResponseEntity<ApiResponse<List<PlaceByCategoryResponse>>> findPlaceByCategory(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("categoryId") Long categoryId,
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.findPlaceByCategory(token.getId(),invitationId,categoryId)));
    }

    //@Operation("summary = "장소 상세 조회하기")
    @GetMapping("/invitations/{invitationId}/place/{placeId}")
    public ResponseEntity<ApiResponse<PlaceDetailResponse>> getDetailPlace(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("placeId") Long placeId,
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.getDetailPlace(token.getId(),invitationId,placeId)));
    }

    //@Operation("summary = "장소 수정하기")
    @PutMapping("/invitations/{invitationId}/place/{placeId}")
    public ResponseEntity<ApiResponse<Long>> updatePlace(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("placeId") Long placeId,
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody UpdatePlaceRequest updatePlaceRequest
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.updatePlace(token.getId(),invitationId,placeId,updatePlaceRequest)));
    }

    //@Operation("summary = "장소 삭제 하기")
    @DeleteMapping("/invitations/{invitationId}/place/{id}")
    public ResponseEntity<ApiResponse<Long>> deletePlace(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("id") Long placeId,
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(placeService.deletePlace(token.getId(),placeId,invitationId)));
    }
}
