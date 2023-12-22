package com.sparta.plusboard.user.service;

import com.sparta.plusboard.global.exception.CustomException;
import com.sparta.plusboard.global.exception.ErrorCode;
import com.sparta.plusboard.user.dto.SignupRequestDto;
import com.sparta.plusboard.user.entity.User;
import com.sparta.plusboard.user.repository.UserRepository;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    public void signup(SignupRequestDto signupRequestDto) throws CustomException {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String passwordConfirm = signupRequestDto.getPasswordConfirm();

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_USERNAME);
        }

        if (password.contains(username)) {
            throw new CustomException(ErrorCode.BAD_PARAMETER);
        }

        if (!password.equals(passwordConfirm)) {
            throw new CustomException(ErrorCode.BAD_PARAMETER);
        }

        User user = new User(username, passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
