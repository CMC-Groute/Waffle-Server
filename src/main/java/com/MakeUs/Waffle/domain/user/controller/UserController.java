package com.MakeUs.Waffle.domain.user.controller;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.dto.*;
import com.MakeUs.Waffle.domain.user.service.UserService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.jwt.JwtAuthenticationToken;
import com.MakeUs.Waffle.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<ApiResponse<UserToken>> signIn(
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

        return ResponseEntity.ok(ApiResponse.of(new UserToken(
                user.getId(),
                principal.getToken(),
                user.getRoles()
                        .toString()
                )
        ));
    }

    //@Operation(summary = "회원가입 컨트롤러")
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Long>> singUp(
            @Valid @RequestBody UserSignUpRequest userSignUpRequest
    ) {
        return ResponseEntity.ok(ApiResponse.of(userService.signUp(userSignUpRequest)));
    }

    //@Operation(summary = "회원수정 컨트롤러")
    @PutMapping(value = "/users")
    public ResponseEntity<ApiResponse<Long>> update(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest
    ){
        return ResponseEntity.ok(ApiResponse.of(userService.update(
                token.getId(),
                userUpdateRequest
        )));
    }

   // @Operation(summary = "회원수정(비밀번호) 컨트롤러")
    @PutMapping("/users/password")
    public ResponseEntity<ApiResponse<Long>> updatePassword(
            @AuthenticationPrincipal JwtAuthentication token,
            @Valid @RequestBody UserPasswordRequest userPasswordRequest
    ){
        return ResponseEntity.ok(ApiResponse.of(userService.updatePassword(
                token.getId(),
                userPasswordRequest
        )));
    }

    @DeleteMapping("/users")
    public ResponseEntity<ApiResponse<Long>> updatePassword(
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(userService.deleteUser(
                token.getId()
        )));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUser(
            @AuthenticationPrincipal JwtAuthentication token
    ){
        return ResponseEntity.ok(ApiResponse.of(userService.getUser(
                token.getId()
        )));
    }
}