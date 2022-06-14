package com.MakeUs.Waffle.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.exception.NotValidPasswordException;
import com.MakeUs.Waffle.error.ErrorCode;
import lombok.Getter;

@Getter
public class UserSignUpRequest {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 주소를 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "비밀번호는 숫자,영문,특수문자를 조합해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String checkPassword;

    @Size(max = 12)
    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

    //private Boolean isAgreedMarketing;

    protected UserSignUpRequest(){}

    public UserSignUpRequest(String email, String password, String checkPassword, String nickname) {
        this.email = email;
        this.password = password;
        this.checkPassword = checkPassword;
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDifferentPassword() {
        if (!this.password.equals(this.checkPassword)) {
            throw new NotValidPasswordException(ErrorCode.CONFLICT_VALUE_ERROR);
        }
        return false;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }
}
