package com.MakeUs.Waffle.domain.push.controller;

import com.MakeUs.Waffle.domain.push.service.PushService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
