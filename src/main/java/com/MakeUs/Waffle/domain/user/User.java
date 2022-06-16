package com.MakeUs.Waffle.domain.user;

import com.MakeUs.Waffle.domain.user.dto.UserUpdateRequest;
import com.MakeUs.Waffle.domain.user.exception.NotSamePasswordException;
import com.MakeUs.Waffle.error.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class User {

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

    private boolean isAgreedMarketing;

    @Builder
    public User(Long id, String nickname, String email, String password, ProfileImage profileImage, boolean isDeleted, boolean isAgreedMarketing) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.roles = Role.ROLE_USER;
        this.password = password;
        this.profileImage = profileImage;
        this.isDeleted = isDeleted;
        this.isAgreedMarketing = isAgreedMarketing;
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
            throw new NotSamePasswordException(ErrorCode.CONFLICT_PASSWORD_ERROR);
        }
    }

    public void updateUserPasswordInfo(PasswordEncoder passwordEncoder, String newPassword){
        this.password = passwordEncoder.encode(newPassword);
    }

    public void updateUserInfo(UserUpdateRequest userUpdateRequest) {
        this.nickname = userUpdateRequest.getNickname();
        this.profileImage = userUpdateRequest.getEnumProfileImage(userUpdateRequest.getProfileImage());
    }
}
