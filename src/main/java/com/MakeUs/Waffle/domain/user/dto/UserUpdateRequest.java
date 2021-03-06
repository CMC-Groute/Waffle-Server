package com.MakeUs.Waffle.domain.user.dto;

import com.MakeUs.Waffle.domain.user.ProfileImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {

    @Size(max = 6)
    private String nickname;

    private String profileImage;

    public UserUpdateRequest(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ProfileImage getEnumProfileImage(String profileImage) {
        return ProfileImage.valueOf(profileImage);
    }

    public String getStringProfileImage(ProfileImage profileImage) {
        return profileImage.toString();
    }
}