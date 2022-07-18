package com.MakeUs.Waffle.domain.push.controller;

import com.MakeUs.Waffle.domain.push.dto.getPushResponse;
import com.MakeUs.Waffle.domain.push.service.PushService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PushController {

    private final PushService pushService;

    public PushController(PushService pushService) {
        this.pushService = pushService;
    }

    //@Operation(summary = "좋아요 조르기")
    @PostMapping("/invitation/{id}/push/likes")
    public ResponseEntity<Void> pushLikes(
            @AuthenticationPrincipal JwtAuthentication token, @PathVariable Long id
    ) {
        pushService.pushLikes(
                token.getId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //알람 조회
    @GetMapping("/push")
    public ResponseEntity<ApiResponse<List<getPushResponse>>> getAlarm(
            @AuthenticationPrincipal JwtAuthentication token){
        return ResponseEntity.ok(ApiResponse.of(pushService.getAlarm(token.getId())));

    }

    //읽음 바꿈
    @PatchMapping("/push/{id}")
    public ResponseEntity<ApiResponse<Long>> updateIsRead(
            @AuthenticationPrincipal JwtAuthentication token,
            @PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.of(pushService.updateIsRead(token.getId(),id)));

    }
}
