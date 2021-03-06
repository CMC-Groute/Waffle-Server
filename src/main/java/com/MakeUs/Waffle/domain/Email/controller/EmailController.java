package com.MakeUs.Waffle.domain.Email.controller;

import javax.validation.Valid;

import com.MakeUs.Waffle.domain.Email.dto.EmailCodeRequest;
import com.MakeUs.Waffle.domain.Email.dto.EmailRequest;
import com.MakeUs.Waffle.domain.Email.dto.TempPwRequest;
import com.MakeUs.Waffle.domain.Email.service.EmailService;
import com.MakeUs.Waffle.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<String>> emailAuth(
            @RequestBody @Valid EmailRequest request
    ) {
        emailService.sendMessage(request.getEmail());
        return ResponseEntity.ok(ApiResponse.of("인증코드 전송 완료"));
    }

    @PostMapping("/email/verify-code")
    public ResponseEntity<ApiResponse<String>> verifyCode(
            @RequestBody @Valid EmailCodeRequest request
    ) {
        emailService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(ApiResponse.of("인증코드 검증 완료"));
    }

    @PostMapping("/email/findPw")
    public ResponseEntity<ApiResponse<String>> sendTempPw(
            @RequestBody @Valid TempPwRequest request
    ) {
        emailService.sendPassword(request);
        return ResponseEntity.ok(ApiResponse.of("인증코드 전송 완료"));
    }
}