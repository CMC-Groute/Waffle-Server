package com.MakeUs.Waffle.domain.user.dto;

import com.MakeUs.Waffle.domain.user.ProfileImage;
import com.MakeUs.Waffle.domain.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailResponse {
    private String nickname;
    private String email;
    private String profileImage;

    @Builder
    public UserDetailResponse(String nickname, String email, String profileImage) {
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }
}
