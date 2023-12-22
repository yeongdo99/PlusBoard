package com.sparta.plusboard.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "닉네임은 필수 입력 값 입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "닉네임은 3자 이상 입력해주세요")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "비밀번호는 최소 4자 이상 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "비밀번호를 다시 입력해주세요")
    private String passwordConfirm;
}
