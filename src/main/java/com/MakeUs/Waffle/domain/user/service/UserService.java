package com.MakeUs.Waffle.domain.user.service;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.dto.UserSignUpRequest;
import com.MakeUs.Waffle.domain.user.dto.UserUpdateRequest;
import com.MakeUs.Waffle.domain.user.exception.DuplicateUserException;
import com.MakeUs.Waffle.domain.user.exception.NotFoundUserException;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DIRECTORY = "static";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public User signIn(String username, String credentials) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundUserException(ErrorCode.NOT_FOUND_RESOURCE_ERROR));

        user.checkPassword(
                passwordEncoder,
                credentials
        );

        return user;
    }

    @Transactional
    public Long signUp(UserSignUpRequest userSignUpRequest) {
        if ((!isDuplicateUser(userSignUpRequest)) && !userSignUpRequest.isDifferentPassword()) {
            userSignUpRequest.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        }

        return userRepository.save(userSignUpRequest.toEntity())
                .getId();
    }

    @Transactional(readOnly = true)
    public boolean isValidEmail(String email){
        checkArgument(isNotEmpty(email),"이메일은 작성해야 합니다.");
        return !userRepository.existsByEmail(email);
    }

    private boolean isDuplicateUser(UserSignUpRequest userSignUpRequest) {
        if (userRepository.existsByEmail(userSignUpRequest.getEmail())) {
            throw new DuplicateUserException(ErrorCode.CONFLICT_VALUE_ERROR);
        }
        return false;
    }

    @Transactional
    public Long update(Long id, UserUpdateRequest userUpdateRequest)
            throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundUserException(ErrorCode.NOT_FOUND_RESOURCE_ERROR));
        userUpdateRequest.setProfileImage(userUpdateRequest.getStringProfileImage(user.getProfileImage()));
        user.updateUserInfo(userUpdateRequest);

        return user.getId();
    }
}
