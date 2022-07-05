package com.MakeUs.Waffle.domain.user.repository;

import java.util.Optional;

import com.MakeUs.Waffle.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.deviceToken = ?1 WHERE u.userId = ?2")
    void updateDeviceTokenByUserId(String deviceToken, Long userId);
}
