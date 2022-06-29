package com.MakeUs.Waffle.domain.user.service;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.dto.UserPasswordRequest;
import com.MakeUs.Waffle.domain.user.dto.UserSignUpRequest;
import com.MakeUs.Waffle.domain.user.dto.UserUpdateRequest;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
import com.MakeUs.Waffle.error.exception.DuplicatedResourceException;
import com.MakeUs.Waffle.error.exception.NotFoundResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                .orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));

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
            throw new DuplicatedResourceException(ErrorCode.DUPLICATE_USER_ERROR);
        }
        return false;
    }

    @Transactional
    public Long update(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        if (userUpdateRequest.getProfileImage() == null) {
            userUpdateRequest.setProfileImage(userUpdateRequest.getStringProfileImage(user.getProfileImage()));
        }
        if (userUpdateRequest.getNickname() == null) {
            userUpdateRequest.setNickname(user.getNickname());
        }
        user.updateUserInfo(userUpdateRequest);

        return user.getId();
    }

    @Transactional
    public Long updatePassword(Long id, UserPasswordRequest userPasswordRequest){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        user.checkPassword(passwordEncoder,userPasswordRequest.getNowPassword());
        if(!userPasswordRequest.isDifferentPassword()){
            user.updateUserPasswordInfo(passwordEncoder, userPasswordRequest.getNewPassword());
        }
        return user.getId();
    }

    @Transactional
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundResourceException(ErrorCode.NOT_FOUND_USER));
        userRepository.delete(user);
    }
}
