package com.MakeUs.Waffle.domain.Email.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.MakeUs.Waffle.domain.user.exception.NotFoundUserException;
import com.MakeUs.Waffle.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final RedisTemplate<String, String> redisTemplate;

    private static final Long EXPIRATION = 180000L;

    public void sendMessage(String receiver) {
        final String code = createCode();
        redisTemplate.opsForValue()
                .set(
                        "EC:" + receiver, code, EXPIRATION, TimeUnit.MILLISECONDS
                );
        MimeMessage message = createMessage(receiver, code);
        emailSender.send(message);
    }

    public void verifyCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get("EC:" + email);

        if (savedCode == null) {
            throw new NotFoundUserException(ErrorCode.INVALID_EMAIL_ERROR);
        }
        if (!savedCode.equals(code)) {
            throw new NotFoundUserException(ErrorCode.NOT_FOUND_EMAIL_CODE);
        }
    }

    private static String createCode() {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            code.append(new Random().nextInt(10));
        }
        return code.toString();
    }

    private MimeMessage createMessage(String receiver, String code) {
        log.info("보내는 대상 : {}", receiver);
        log.info("인증 번호 : {}", code);
        try {

            MimeMessage message = emailSender.createMimeMessage();

            String codeWithDash = code.substring(0, 3) + "-" + code.substring(3, 6);
            message.addRecipients(RecipientType.TO, receiver);
            message.setSubject("Waffle 확인 코드: " + codeWithDash);

            String msg = "<head>\n <style>\n @font-face {\n font-family: 'Pretendard-Regular';\n"
                    + "format('woff');\n font-weight: 400;\n font-style: normal;\n }\n "
                    + "@font-face {\n font-family: 'Pretendard-ExtraBold';\n "
                    + "format('woff');\n font-weight: 800;\n font-style: normal;\n }\n </style>\n"
                    + "</head>\n<body style=\"font-family: Pretendard-Regular\">\n"
                    + "</div>\n\n\n</div>\n\n<div style=\"background-color: white; padding-left: 10%; margin-top: 4.5%\">\n "
                    + "<div>\n <h1 style=\"font-family: Pretendard-ExtraBold;\">이메일 인증 코드 확인</h1>\n </div>\n"
                    + "<div>\n <p style=\"font-family: Pretendard-ExtraBold;\">아래 확인 코드를 Waffle 이메일 인증란에 입력해주세요.</p>\n"
                    + "<p style=\"font-family: Pretendard-ExtraBold;\">(해당 코드는 ";
            msg += LocalDateTime.now()
                    .plusMinutes(3)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            msg += "에 만료됩니다.)</p> </div>\n <div>\n <div\n style=\"width: fit-content; height: auto; background-color: #E7E6E6; font-size: 3em; border-radius: 5px; padding: 5px; margin-top : 2.5%; font-family: Pretendard-ExtraBold;\">\n ";
            msg += code;
            msg += "\n</div>\n</div>\n</div>\n\n<div style=\"margin-top: 5%; margin-bottom: 5%;\">\n"
                    + "<hr>\n</div>\n\n<div style=\"padding-left: 10%; font-size: small;\">\n"
                    + "<div style=\"font-family: Pretendard-ExtraBold;\">\n<i>본 메일은 발신 전용입니다.</i>\n</div>\n<div>\n"
                    + "<p style=\"font-family: Pretendard-ExtraBold;\">ⓒ 2021. waffle, Inc Co. all rights reserved.</p>\n</div>\n</div>\n</body>";

            message.setText(msg, "utf-8", "html");
            message.setFrom(new InternetAddress("waffle.mailg@gmail.com", "waffle"));

            return message;
        } catch (Exception e) {
            throw new MailSendException(e.getMessage());
        }
    }

}
