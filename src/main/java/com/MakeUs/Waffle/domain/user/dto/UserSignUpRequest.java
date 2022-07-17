package com.MakeUs.Waffle.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.MakeUs.Waffle.domain.user.ProfileImage;
import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserSignUpRequest {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 주소를 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$", message = "비밀번호는 숫자,영문을 조합해야 합니다.")
    private String password;


    @Size(max = 6)
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

    private Boolean isAgreedMarketing = false;

    @NotBlank(message = "프로필을 지정해 주세요.")
    private String profileImage;

    protected UserSignUpRequest(){}

    public UserSignUpRequest(String email, String password, String nickname, Boolean isAgreedMarketing, String profileImage) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isAgreedMarketing = isAgreedMarketing;
        this.profileImage = profileImage;
    }

    public ProfileImage getEnumProfileImage(String profileImage) {
        return ProfileImage.valueOf(profileImage);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .isAgreedMarketing(isAgreedMarketing)
                .isAgreedAlarm(true)
                .profileImage(this.getEnumProfileImage(profileImage))
                .build();
    }
}
