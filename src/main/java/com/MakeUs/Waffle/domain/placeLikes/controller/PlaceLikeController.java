package com.MakeUs.Waffle.domain.placeLikes.controller;

import com.MakeUs.Waffle.domain.placeLikes.service.PlaceLikeService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceLikeController {

    private final PlaceLikeService placeLikeService;

    public PlaceLikeController(PlaceLikeService placeLikeService) {
        this.placeLikeService = placeLikeService;
    }

    //@Operation(summary = "장소 좋아요 생성 컨트롤러")
    @PostMapping("/invitation/place/{id}/likes")
    public ResponseEntity<ApiResponse<Long>> saveCommentLikes(
            @AuthenticationPrincipal JwtAuthentication token, @PathVariable Long id
    ) {

        return ResponseEntity.ok(ApiResponse.of(placeLikeService.savePlaceLike(
                token.getId(),
                id
        )));
    }

    //@Operation(summary = "장소 좋아요 삭제 컨트롤러")
    @DeleteMapping("/invitation/place/{id}/likes")
    public ResponseEntity<ApiResponse<Long>> deleteCommentLikes(
            @AuthenticationPrincipal JwtAuthentication token, @PathVariable Long id
    ) {
        return ResponseEntity.ok(ApiResponse.of( placeLikeService.deletePlaceLike(
                token.getId(), id)
        ));
    }
}
