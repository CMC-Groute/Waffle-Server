package com.MakeUs.Waffle.domain.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "email_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailCode {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "email_code", nullable = false)
    private String emailCode;

    private LocalDateTime expiration;

    @Builder
    public EmailCode(Long id, String email, String emailCode, LocalDateTime expiration) {
        this.id = id;
        this.email = email;
        this.emailCode = emailCode;
        this.expiration = expiration;
    }


    public void changeEmailCode(String emailCode,LocalDateTime expiration) {
        this.emailCode = emailCode;
        this.expiration = expiration;
    }

}