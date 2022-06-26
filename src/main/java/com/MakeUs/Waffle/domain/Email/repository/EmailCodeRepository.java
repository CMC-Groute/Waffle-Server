package com.MakeUs.Waffle.domain.Email.repository;

import java.util.Optional;

import com.MakeUs.Waffle.domain.Email.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {

    boolean existsByEmailAndEmailCode(String email, String code);

    Optional<EmailCode> findByEmail(String receiver);

    boolean existsByEmail(String email);

    EmailCode getByEmail(String receiver);
}