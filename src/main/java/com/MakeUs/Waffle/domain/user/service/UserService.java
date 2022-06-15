package com.MakeUs.Waffle.domain.user.service;

import com.MakeUs.Waffle.domain.user.User;
import com.MakeUs.Waffle.domain.user.dto.UserSignUpRequest;
import com.MakeUs.Waffle.domain.user.exception.DuplicateUserException;
import com.MakeUs.Waffle.domain.user.exception.NotFoundUserException;
import com.MakeUs.Waffle.domain.user.repository.UserRepository;
import com.MakeUs.Waffle.error.ErrorCode;
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



//    @Transactional(readOnly = true)
//    public UserResponse findUser(Long id) {
//        return findActiveUser(id).toResponse();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<User> findUserByProviderAndProviderId(String provider, String providerId) {
//        return userRepository.findByProviderAndProviderId(
//                provider,
//                providerId
//        );
//    }
//
//    @Transactional
//    public Long updatePassword(Long id, UserPasswordRequest userPasswordRequest){
//        User user = findActiveUser(id);
//        user.checkPassword(passwordEncoder,userPasswordRequest.getNowPassword());
//        if(!userPasswordRequest.isDifferentPassword()){
//            user.updateUserPasswordInfo(passwordEncoder, userPasswordRequest);
//        }
//        return user.getId();
//    }
//
//    @Transactional
//    public Long delete(Long id) {
//        userRepository.deleteById(findActiveUser(id).getId());
//        return id;
//    }
//
//    @Transactional(readOnly = true)
//    public User findActiveUser(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundUserException(ErrorCode.NOT_FOUND_RESOURCE_ERROR));
//    }

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
}
