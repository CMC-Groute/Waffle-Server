package com.MakeUs.Waffle.domain.user.dto;

import com.MakeUs.Waffle.domain.user.exception.NotSamePasswordException;
import com.MakeUs.Waffle.error.ErrorCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class UserPasswordRequest {
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 6, message = "비밀번호는 최소 6글자 이상입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$", message = "비밀번호는 숫자,영문을 조합해야 합니다.")
    private String nowPassword;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 6, message = "비밀번호는 최소 6글자 이상입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$", message = "비밀번호는 숫자,영문을 조합해야 합니다.")
    private String newPassword;

    @NotBlank(message = "변경할 비밀번호를 한번 더 입력해 주세요.")
    @Size(min = 6, message = "비밀번호는 최소 6글자 이상입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$" , message = "변경할 비밀번호와 일치하지 않습니다.")
    private String newPasswordCheck;

    public boolean isDifferentPassword() {
        if (!this.newPassword.equals(this.newPasswordCheck)) {
            throw new NotSamePasswordException(ErrorCode.CONFLICT_PASSWORD_ERROR);
        }
        return false;
    }
}