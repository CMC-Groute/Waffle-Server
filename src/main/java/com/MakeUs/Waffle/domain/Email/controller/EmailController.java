package com.MakeUs.Waffle.domain.Email.controller;

import javax.validation.Valid;

import com.MakeUs.Waffle.domain.Email.dto.EmailCodeRequest;
import com.MakeUs.Waffle.domain.Email.dto.EmailRequest;
import com.MakeUs.Waffle.domain.Email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

//    @PostMapping("/emailConfirm")
//    //@ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
//    public ResponseEntity<String> emailConfirm(
//            @RequestBody @Valid EmailRequest request) throws Exception {
//
//        String confirm = emailService.sendSimpleMessage(request);
//
//        return ResponseEntity.ok(confirm);
//    }

    @PostMapping("/email")
    public ResponseEntity<String> emailAuth(
            @RequestBody @Valid EmailRequest request
    ) {
        emailService.sendMessage(request.getEmail());
        return ResponseEntity.ok("인증코드 전송 완료");
    }

    @PostMapping("/email/verify-code")
    public ResponseEntity<String> verifyCode(
            @RequestBody @Valid EmailCodeRequest request
    ) {
        emailService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok("인증코드 검증 완료");
    }
}