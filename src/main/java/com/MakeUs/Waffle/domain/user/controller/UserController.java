package com.MakeUs.Waffle.domain.user.controller;

import javax.validation.Valid;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.dto.UserSignInRequest;
import com.MakeUs.Waffle.domain.user.dto.UserSignUpRequest;
import com.MakeUs.Waffle.domain.user.dto.UserToken;
import com.MakeUs.Waffle.domain.user.service.UserService;
import com.MakeUs.Waffle.jwt.JwtAuthentication;
import com.MakeUs.Waffle.jwt.JwtAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}