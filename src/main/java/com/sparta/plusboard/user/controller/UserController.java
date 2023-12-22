package com.sparta.plusboard.user.controller;

import com.sparta.plusboard.global.exception.CommonResponseDto;
import com.sparta.plusboard.global.exception.CustomException;
import com.sparta.plusboard.user.dto.SignupRequestDto;
import com.sparta.plusboard.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws CustomException {

            userService.signup(signupRequestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("회원가입을 성공하였습니다.", HttpStatus.OK.value()));
    }
}
