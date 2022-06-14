package com.MakeUs.Waffle.domain.Email.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailRequest {

    @NotBlank
    private String email;

    public EmailRequest(String email) {
        this.email = email;
    }
}