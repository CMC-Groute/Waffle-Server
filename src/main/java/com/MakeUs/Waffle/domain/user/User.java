package com.MakeUs.Waffle.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String password;

    private String profileImage;

    @Column(nullable = false, columnDefinition = "TINYINT default false")
    private boolean isDeleted;

}
