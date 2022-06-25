package com.MakeUs.Waffle.domain.user.controller;

import javax.validation.Valid;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.dto.*;
import com.MakeUs.Waffle.domain.user.service.UserService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.jwt.JwtAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
@RestController
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public UserController(
            AuthenticationManager authenticationManager, UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    //@Operation(summary = "로그인 JWT 토큰 발행 컨트롤러")
    @PostMapping("/users/login")
    public ResponseEntity<UserToken> signIn(
            @RequestBody UserSignInRequest userSignInRequest
    ) {

        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(
                userSignInRequest.getEmail(),
                userSignInRequest.getPassword()
        );

        Authentication resultToken = authenticationManager.authenticate(authenticationToken);
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) resultToken;
        JwtAuthentication principal = (JwtAuthentication) jwtAuthenticationToken.getPrincipal();
        User user = (User) jwtAuthenticationToken.getDetails();

        return ResponseEntity.ok(new UserToken(
                user.getId(),
                principal.getToken(),
                user.getRoles()
                        .toString()
        ));
    }

    //@Operation(summary = "회원가입 컨트롤러")
    @PostMapping("/users")
    public ResponseEntity<Long> singUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        return ResponseEntity.ok(userService.signUp(userSignUpRequest));
    }

    //@Operation(summary = "회원수정 컨트롤러")
    @PutMapping(value = "/users")
    public ResponseEntity<Long> update(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest
    ){
        return ResponseEntity.ok(userService.update(
                token.getId(),
                userUpdateRequest
        ));
    }

   // @Operation(summary = "회원수정(비밀번호) 컨트롤러")
    @PutMapping("/users/password")
    public ResponseEntity<Long> updatePassword(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody UserPasswordRequest userPasswordRequest
    ){
        return ResponseEntity.ok(userService.updatePassword(
                token.getId(),
                userPasswordRequest
        ));
    }
}