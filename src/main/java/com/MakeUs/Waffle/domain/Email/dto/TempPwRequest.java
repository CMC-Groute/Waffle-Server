package com.MakeUs.Waffle.domain.Email.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class TempPwRequest {
    @NotBlank
    private String email;

    public TempPwRequest(String email) {
        this.email = email;
    }
}
