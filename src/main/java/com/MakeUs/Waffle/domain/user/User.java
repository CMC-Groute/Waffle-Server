package com.MakeUs.Waffle.domain.user;

import com.MakeUs.Waffle.domain.BaseEntity;
import com.MakeUs.Waffle.domain.user.dto.UserDetailResponse;
import com.MakeUs.Waffle.domain.user.dto.UserUpdateRequest;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.NotMatchResourceException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id =?")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role roles;

    private String password;

    @Enumerated(EnumType.STRING)
    private ProfileImage profileImage;

    @Column(nullable = false, columnDefinition = "TINYINT default false")
    private boolean isDeleted;

    @Column(nullable = false, columnDefinition = "TINYINT default true")
    private boolean isAgreedAlarm;

    private boolean isAgreedMarketing;

    @Column(name = "deviceToken")
    private String deviceToken;

    @Builder
    public User(boolean isAgreedAlarm, String deviceToken, Long id, String nickname, String email, String password, ProfileImage profileImage, boolean isDeleted, boolean isAgreedMarketing) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.roles = Role.ROLE_USER;
        this.password = password;
        this.profileImage = profileImage;
        this.isDeleted = isDeleted;
        this.isAgreedMarketing = isAgreedMarketing;
        this.deviceToken = deviceToken;
        this.isAgreedAlarm = isAgreedAlarm;
    }

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));

        return grantedAuthorities;
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(
                credentials,
                password
        )) {
            throw new NotMatchResourceException(ErrorCode.NOT_SAME_PASSWORD);
        }
    }

    public void updateUserPasswordInfo(PasswordEncoder passwordEncoder, String newPassword){
        this.password = passwordEncoder.encode(newPassword);
    }

    public void updateUserInfo(UserUpdateRequest userUpdateRequest) {
        this.nickname = userUpdateRequest.getNickname();
        this.profileImage = userUpdateRequest.getEnumProfileImage(userUpdateRequest.getProfileImage());
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void updateIsAgreeAlarm(boolean isAgreedAlarm) {
        this.isAgreedAlarm = isAgreedAlarm;
    }

    public UserDetailResponse toUserDetailResponse(){
        return UserDetailResponse.builder()
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage.toString())
                .build();
    }
}
